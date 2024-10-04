package com.example.MovieBooking.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

//public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
//        String role = authentication.getAuthorities().toString().replace("[", "").replace("]", "");
//        System.out.println("this is role" + role);
//        if (role.equals("MEMBER")) {
//            setDefaultTargetUrl("/index");
//        }
//        super.onAuthenticationSuccess(request, response, authentication);
//    }
//}


public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String role = authentication.getAuthorities().toString().replace("[", "").replace("]", "");
        if (role.equals("MEMBER")) {
            response.sendRedirect("/home");
        } else if(role.equals("ADMIN")) {
            response.sendRedirect("/homeAdmin");
        } else if(role.equals("EMPLOYEE")) {
            response.sendRedirect("/homeEmployee");
        }
    }
}
