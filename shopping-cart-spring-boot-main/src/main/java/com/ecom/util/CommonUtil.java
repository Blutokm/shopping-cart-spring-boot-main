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

        helper.setFrom("blutokit@gmail.com", "Shopping Cart");
        helper.setTo(reciepentEmail);

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + url + "\">Change my password</a></p>";

        helper.setSubject("Password Reset");
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

        msg = "<p>Hello [[name]],</p>"
                + "<p>Thank you for your order. Current status: <b>[[orderStatus]]</b>.</p>"
                + "<p><b>Product Details:</b></p>"
                + "<p>Name : [[productName]]</p>"
                + "<p>Category : [[category]]</p>"
                + "<p>Quantity : [[quantity]]</p>"
                + "<p>Price : [[price]]</p>"
                + "<p>Payment Type : [[paymentType]]</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String email = null;
        String firstName = "Customer";

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

        helper.setFrom("blutokit@gmail.com", "Shopping Cart");
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

        helper.setSubject("Product Order Status");
        helper.setText(msg, true);
        //mailSender.send(message);
        return true;
    }

    public UserDtls getLoggedInUserDetails(Principal p) {
        String email = p.getName();
        return userService.getUserByEmail(email);
    }
}
