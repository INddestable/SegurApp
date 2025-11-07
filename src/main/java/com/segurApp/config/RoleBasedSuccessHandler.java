package com.segurApp.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class RoleBasedSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
                                        throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean admin = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean user = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));

        if (admin) {
            response.sendRedirect(request.getContextPath() + "/administradores/dashboard");
        } else if (user) {
            response.sendRedirect(request.getContextPath() + "/clientes/dashboard");
        } else {
            response.sendRedirect(request.getContextPath() + "/index");
        }
    }
}
