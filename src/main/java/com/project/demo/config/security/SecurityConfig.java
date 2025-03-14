package com.project.demo.config.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.project.demo.api.role.application.MenuRoleService;
import com.project.demo.config.security.application.CustomUserDetailsService;
import com.project.demo.config.security.handler.CustomLoginFailureHandler;
import com.project.demo.config.security.handler.CustomLoginSuccessHandler;
import com.project.demo.config.security.handler.CustomLogoutSuccessHandler;
import com.project.demo.config.security.handler.CustomSessionExpiredHandler;
import com.project.demo.config.security.infrastructure.MenuAuthorizationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final MenuRoleService menuRoleService;
    private final CustomLoginSuccessHandler loginSuccessHandler;
    private final CustomLoginFailureHandler loginFailureHandler;
    private final CustomLogoutSuccessHandler logoutSuccessHandler;
    private final CustomSessionExpiredHandler sessionExpiredHandler;
    private final MenuAuthorizationFilter menuAuthorizationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        List<String> guestUrls = menuRoleService.getGuestAccessibleUrls();
        
        http
            .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
            .addFilterBefore(menuAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/error/**", "/pagination").permitAll();
                auth.requestMatchers("/css/**", "/images/**", "/js/**").permitAll();

                // GUEST_ROLE 접근 URL 동적 추가
                guestUrls.forEach(url -> {
                    if ( StringUtils.hasText(url) )
                        auth.requestMatchers(url).permitAll();
                });

                auth.anyRequest().authenticated();
            })
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
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
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
