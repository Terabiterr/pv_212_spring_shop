package com.example.Shop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() { //hash password
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers( //pages for not authed
                        "/",
                        "/products",
                        "/auth/register",
                        "/auth/login"
                )
                        .permitAll()
                        .anyRequest() //pages for not authed
                        .authenticated())
                .formLogin(form -> form.loginPage("/login") //custom page for login
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logout -> logout.logoutUrl("/logout") //custom page for logout
                        .permitAll());
        return http.build();
    }
}
