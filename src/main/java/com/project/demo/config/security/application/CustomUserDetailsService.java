package com.project.demo.config.security.application;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.demo.api.user.domain.UserEntity;
import com.project.demo.api.user.infrastructure.UserRepository;
import com.project.demo.api.user.value.ActiveYn;
import com.project.demo.common.constant.LoginMsgKey;
import com.project.demo.common.util.MsgUtil;
import com.project.demo.config.security.application.dto.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MsgUtil msgUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        UserEntity user = userRepository.findByUserIdAndActiveYn(username, ActiveYn.Y)
                            .orElseThrow(() -> new UsernameNotFoundException(msgUtil.getMessage(LoginMsgKey.USER_NOT_FOUND.getKey())));
        
        return CustomUserDetails.of(user);
    }
}
