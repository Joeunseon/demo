package com.project.demo.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import com.project.demo.config.security.handler.CustomLoginFailureHandler;
import com.project.demo.config.security.handler.CustomLoginSuccessHandler;
import com.project.demo.config.security.handler.CustomSessionExpiredHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/join").permitAll()
                .requestMatchers("/error", "/error/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(login -> login 
                .loginPage("/login")
                .successHandler(new CustomLoginSuccessHandler())
                .failureHandler(new CustomLoginFailureHandler())
                .permitAll()
            )
            .sessionManagement(session -> session
                .invalidSessionUrl("/error/sessionTimeOut")
                .maximumSessions(1)
                .expiredSessionStrategy(new CustomSessionExpiredHandler())
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );

        return http.build();
    }
}
