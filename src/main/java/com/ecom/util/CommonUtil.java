package com.ecom.util;

import java.io.UnsupportedEncodingException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.ecom.model.ProductOrder;
import com.ecom.model.UserDtls;
import com.ecom.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CommonUtil {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    public Boolean sendMail(String url, String reciepentEmail)
            throws UnsupportedEncodingException, MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("blutokit@gmail.com", "ShopPKTH");
        helper.setTo(reciepentEmail);

        String content = "<p>Xin chào,</p>"
                + "<p>Bạn vừa yêu cầu đặt lại mật khẩu cho tài khoản của mình trên hệ thống.</p>"
                + "<p>Vui lòng nhấp vào liên kết bên dưới để tiến hành đổi mật khẩu mới:</p>"
                + "<p><a href=\"" + url + "\" style=\"display: inline-block; padding: 10px 20px; color: #fff; background-color: #007bff; text-decoration: none; border-radius: 5px;\">Đổi mật khẩu ngay</a></p>"
                + "<p><i>Lưu ý: Nếu bạn không yêu cầu đổi mật khẩu, vui lòng bỏ qua email này.</i></p>";

        helper.setSubject("Yêu cầu đặt lại mật khẩu - ShopPKTH");
        helper.setText(content, true);
        
        mailSender.send(message);
        return true;
    }

    public static String generateUrl(HttpServletRequest request) {
        String siteUrl = request.getRequestURL().toString();
        return siteUrl.replace(request.getServletPath(), "");
    }

    private String msg = null;

    public Boolean sendMailForProductOrder(ProductOrder order, String status) throws Exception {

        msg = "<p>Xin chào <b>[[name]]</b>,</p>"
                + "<p>Cảm ơn bạn đã mua sắm tại ShopPKTH. Trạng thái đơn hàng của bạn hiện tại là: <b style=\"color: red;\">[[orderStatus]]</b>.</p>"
                + "<p><b>Chi tiết sản phẩm:</b></p>"
                + "<ul>"
                + "<li><b>Tên sản phẩm:</b> [[productName]]</li>"
                + "<li><b>Danh mục:</b> [[category]]</li>"
                + "<li><b>Số lượng:</b> [[quantity]]</li>"
                + "<li><b>Đơn giá:</b> [[price]] ₫</li>"
                + "<li><b>Hình thức thanh toán:</b> [[paymentType]]</li>"
                + "</ul>"
                + "<p>Chúng tôi sẽ liên hệ với bạn trong thời gian sớm nhất. Chúc bạn một ngày vui vẻ!</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String email = null;
        String firstName = "Quý khách";

        if (order.getOrderAddress() != null) {
            email = order.getOrderAddress().getEmail();
            if (order.getOrderAddress().getFirstName() != null) {
                firstName = order.getOrderAddress().getFirstName();
            }
        }

        if (email == null || email.isEmpty()) {
            System.out.println("⚠ Không có email người nhận, bỏ qua gửi mail đơn hàng.");
            return false;
        }

        helper.setFrom("blutokit@gmail.com", "ShopPKTH");
        helper.setTo(email);

        msg = msg.replace("[[name]]", firstName);
        msg = msg.replace("[[orderStatus]]", status);
        msg = msg.replace("[[productName]]",
                order.getProduct() != null ? order.getProduct().getTitle() : "N/A");
        msg = msg.replace("[[category]]",
                order.getProduct() != null ? order.getProduct().getCategory() : "N/A");
        msg = msg.replace("[[quantity]]",
                order.getQuantity() != null ? order.getQuantity().toString() : "0");
        msg = msg.replace("[[price]]",
                order.getPrice() != null ? order.getPrice().toString() : "0");
        msg = msg.replace("[[paymentType]]",
                order.getPaymentType() != null ? order.getPaymentType() : "N/A");

        helper.setSubject("Cập nhật trạng thái đơn hàng - ShopPKTH");
        helper.setText(msg, true);

        return true;
    }

    public UserDtls getLoggedInUserDetails(Principal p) {
        String email = p.getName();
        return userService.getUserByEmail(email);
    }
}
