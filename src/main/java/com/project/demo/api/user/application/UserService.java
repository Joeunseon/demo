package com.project.demo.api.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.demo.api.user.infrastructure.UserRepository;
import com.project.demo.common.constant.Login;
import com.project.demo.common.util.MsgUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final MsgUtil msgUtil;

    @Transactional
    public void updateLastLoginDt(Long userSeq) {
        
        Integer result = userRepository.updateLastLoginTime(userSeq);

        if ( result > 0 ) {
            log.info(msgUtil.getMessage(Login.SUCCUESS_LAST_LOGIN.getKey(), userSeq));
        } else {
            log.warn(msgUtil.getMessage(Login.FAILED_LAST_LOGIN.getKey(), userSeq));
        }
    }
}
