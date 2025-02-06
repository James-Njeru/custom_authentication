package com.example.gdi_auth.config.security.filters;

import com.example.gdi_auth.config.security.authentication.CustomAuthentication;
import com.example.gdi_auth.config.security.managers.CustomAuthenticationManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    private final CustomAuthenticationManager customAuthenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String username = request.getParameter("username"); // Assuming you pass the username as a parameter
        String password = request.getParameter("password"); // Assuming you pass the password as a parameter

        if (username != null && password != null) {
            CustomAuthentication customAuthentication = new CustomAuthentication(false, username, password, null);

            try {
                Authentication authentication = customAuthenticationManager.authenticate(customAuthentication);

                // If authentication is successful, set the Authentication object in the SecurityContext
                if (authentication.isAuthenticated()){
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }catch (AuthenticationException e){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
            }
        }

        // Continue the filter chain if authentication was successful or skipped
        filterChain.doFilter(request, response);
    }
}
