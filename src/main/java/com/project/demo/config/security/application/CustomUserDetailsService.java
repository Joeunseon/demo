package com.project.demo.config.security.application;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.demo.api.user.domain.UserEntity;
import com.project.demo.api.user.infrastructure.UserRepository;
import com.project.demo.api.user.value.ActiveYn;
import com.project.demo.config.security.application.dto.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        UserEntity user = userRepository.findByUserIdAndActiveYn(username, ActiveYn.Y)
                            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        
        return CustomUserDetails.of(user);
    }
}
