package com.ecom.controller;

import com.ecom.config.VNPayConfig;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller xử lý thanh toán VNPay và callback sau khi thanh toán.
 */
@Controller
public class PaymentController {

    /**
     * ✅ Hàm static dùng để tạo URL thanh toán VNPay.
     * Được gọi trực tiếp từ UserController.saveOrder(...)
     */
	public static String buildVnPayUrl(HttpServletRequest request, long amount, String orderId) throws Exception {
	    String vnp_Version = "2.1.0";
	    String vnp_Command = "pay";
	    String orderType = "other";
	    long amountInVND = amount * 100; // VNPay yêu cầu nhân 100

	    String vnp_TxnRef = orderId; 
	    String vnp_IpAddr = getClientIpAddress(request);
	    String vnp_TmnCode = VNPayConfig.vnp_TmnCode;

        // --- Các tham số bắt buộc gửi tới VNPay ---
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amountInVND));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toán đơn hàng: " + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        // --- Thêm thời gian tạo và hết hạn giao dịch ---
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15); // Hết hạn sau 15 phút
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // --- Sắp xếp key theo thứ tự alphabet ---
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (Iterator<String> itr = fieldNames.iterator(); itr.hasNext();) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
      
                hashData.append(fieldName).append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
              
                query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.toString()))
                        .append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        // --- Tạo chữ ký bảo mật SHA512 ---
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);

        // --- Trả về URL đầy đủ để redirect ---
        return VNPayConfig.vnp_Url + "?" + query.toString();
    }

    /**
     * ✅ Hàm phụ lấy địa chỉ IP của người dùng
     */
    private static String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * ✅ Hàm callback khi VNPay trả kết quả về (redirect URL)
     */
    @Autowired
    private com.ecom.service.OrderService orderService;

    @GetMapping("/payment/vnpay-return")
    public String vnpayReturn(@RequestParam Map<String, String> params) {
        String responseCode = params.get("vnp_ResponseCode");
        String txnRef = params.get("vnp_TxnRef");

        if ("00".equals(responseCode)) {
            return "redirect:/user/success";
        } else {
            try {
                com.ecom.model.ProductOrder order = orderService.getOrdersByOrderId(txnRef);
                if (order != null) {
                    orderService.updateOrderStatus(order.getId(), "Hủy"); 
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return "redirect:/user/order?error=payment";
        }
    }
}
