package com.example.gdi_auth.config;

import com.example.gdi_auth.config.security.filters.CustomAuthenticationFilter;
import com.example.gdi_auth.repository.UsersRepository;
import com.example.gdi_auth.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final CustomAuthenticationFilter customAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //The old way
        /*return http.addFilterAt(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests().anyRequest().authenticated()
                .and().build();*/

        //The new way from Spring Security 6.1+
        return http
                .addFilterAt(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request -> request
                        .anyRequest().authenticated()
                        //.anyRequest().permitAll()
                        //.requestMatchers("/admin/**").hasRole("ADMIN")
                        //.anyRequest().hasAuthority("read")
                        //.anyRequest().access()
                )
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UsersRepository usersRepository) {
        return new CustomUserDetailsService(usersRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
