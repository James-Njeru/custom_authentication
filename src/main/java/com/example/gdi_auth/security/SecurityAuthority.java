package com.example.gdi_auth.security;

import com.example.gdi_auth.entity.Authorities;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class SecurityAuthority implements GrantedAuthority {
    private final Authorities authorities;

    @Override
    public String getAuthority() {
        return authorities.getName();
    }
}
