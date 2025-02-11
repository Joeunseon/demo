package com.project.demo.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.project.demo.config.security.application.CustomUserDetailsService;
import com.project.demo.config.security.handler.CustomLoginFailureHandler;
import com.project.demo.config.security.handler.CustomLoginSuccessHandler;
import com.project.demo.config.security.handler.CustomLogoutSuccessHandler;
import com.project.demo.config.security.handler.CustomSessionExpiredHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final CustomLoginSuccessHandler loginSuccessHandler;
    private final CustomLoginFailureHandler loginFailureHandler;
    private final CustomLogoutSuccessHandler logoutSuccessHandler;
    private final CustomSessionExpiredHandler sessionExpiredHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/join").permitAll()
                .requestMatchers("/error/**").permitAll()
                .requestMatchers("/css/**", "/images/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(login -> login 
                .loginPage("/login")
                .loginProcessingUrl("/api/login")
                .usernameParameter("userId")
                .passwordParameter("userPwd")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .permitAll()
            )
            .sessionManagement(session -> session
                .maximumSessions(1)
                .expiredSessionStrategy(sessionExpiredHandler)
            )
            .logout(logout -> logout
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
