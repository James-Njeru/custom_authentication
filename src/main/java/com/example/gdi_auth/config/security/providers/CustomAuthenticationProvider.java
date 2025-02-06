package com.example.gdi_auth.config.security.providers;

import com.example.gdi_auth.config.security.authentication.CustomAuthentication;
import com.example.gdi_auth.security.CustomUserDetails;
import com.example.gdi_auth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomAuthentication customAuthentication = (CustomAuthentication) authentication;

        String username = customAuthentication.getUsername();
        String password = customAuthentication.getPassword();

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if (userDetails != null && password.equals(userDetails.getPassword())) {

            CustomAuthentication authentication1 = new CustomAuthentication(true, username, password,
                    userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
            authentication1.setAuthenticated(true);

            return authentication1;
        }

        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthentication.class.equals(authentication);
    }
}
