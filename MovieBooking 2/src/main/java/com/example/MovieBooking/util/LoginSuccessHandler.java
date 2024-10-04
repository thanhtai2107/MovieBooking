package com.example.MovieBooking.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * @author Hoang Thanh Tai
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * Specify which page to redirect to after login successfully
     *
     * @author Hoang Thanh Tai
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
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
