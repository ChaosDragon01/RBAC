package com.example.rbac.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService; // Import added
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.rbac.security.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final LoginSuccessHandler loginSuccessHandler;
    private final UserDetailsService userDetailsService; // 1. Define the field

    // 2. Inject it via the Constructor
    public SecurityConfig(LoginSuccessHandler loginSuccessHandler, UserDetailsService userDetailsService) {
        this.loginSuccessHandler = loginSuccessHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/register", "/styles/**", "/forgot-password").permitAll()
                // Allow both ADMIN and ADMINISTRATOR
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/home/**", "/profile/**").hasAnyRole("USER", "ADMIN", "ADMINISTRATOR")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(loginSuccessHandler)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .deleteCookies("JSESSIONID", "remember-me") // Cleanup on logout
            )
            .rememberMe(remember -> remember
                .key("uniqueAndSecretKey")
                .tokenValiditySeconds(86400) // 1 Day
                .userDetailsService(userDetailsService) // 3. Now this variable exists!
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}