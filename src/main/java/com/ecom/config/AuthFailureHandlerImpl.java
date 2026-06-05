package com.ecom.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.ecom.model.UserDtls;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;
import com.ecom.util.AppConstant;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        String email = request.getParameter("username");

        UserDtls userDtls = userRepository.findByEmail(email);

        if (userDtls != null) {

            Boolean enabled = userDtls.getIsEnable();
            Boolean nonLocked = userDtls.getAccountNonLocked();

            if (enabled == null) enabled = false;
            if (nonLocked == null) nonLocked = false;

            if (enabled) {
                if (nonLocked) {
                    if (userDtls.getFailedAttempt() < AppConstant.ATTEMPT_TIME) {
                        userService.increaseFailedAttempt(userDtls);
                        
                        exception = new BadCredentialsException("Email hoặc mật khẩu không đúng");
                        
                    } else {
                        userService.userAccountLock(userDtls);
                        exception = new LockedException("Tài khoản của bạn đã bị khóa do nhập sai 3 lần");
                    }
                } else {
                    if (userService.unlockAccountTimeExpired(userDtls)) {
                        exception = new LockedException("Tài khoản của bạn đã được mở khóa! Vui lòng đăng nhập lại");
                    } else {
                        exception = new LockedException("Tài khoản của bạn đang bị khóa! Vui lòng thử lại sau");
                    }
                }
            } else {
                exception = new LockedException("Tài khoản của bạn chưa được kích hoạt");
            }

        } else {
            exception = new BadCredentialsException("Email hoặc mật khẩu không đúng");
        }

        super.setDefaultFailureUrl("/signin?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}