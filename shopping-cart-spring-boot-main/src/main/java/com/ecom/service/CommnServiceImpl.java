package com.ecom.service;

import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class CommnServiceImpl implements CommonService {

    @Value("${rupee.sign:₫}")
    private String rupeeSign;

    public String rupeeSign() {
        return rupeeSign;
    }

    public String formatCurrency(double amount) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return nf.format(amount);
    }

    @Override
    public void removeSessionMessage() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes()))
                .getRequest();
        HttpSession session = request.getSession();
        session.removeAttribute("succMsg");
        session.removeAttribute("errorMsg");
    }
}
