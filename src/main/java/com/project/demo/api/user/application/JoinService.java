package com.project.demo.api.user.application;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.demo.api.user.infrastructure.JoinRepository;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.JoinMsgKey;
import com.project.demo.common.util.MsgUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JoinService {

    private final JoinRepository joinRepository;
    private final MsgUtil msgUtil;

    private static final List<String> FORBIDDEN_WORDS = Arrays.asList("admin", "system", "root");

    public ApiResponse<Void> checkDuplicateUserId(String userId) {

        String lowerCaseId = userId.toLowerCase();
        if ( FORBIDDEN_WORDS.stream().anyMatch(lowerCaseId::contains) ) 
            return ApiResponse.error(HttpStatus.BAD_REQUEST, msgUtil.getMessage(JoinMsgKey.ID_FORBIDDEN_WORDS.getKey()));
        
        if ( joinRepository.countByUserId(userId) > 0 )
            return ApiResponse.error(HttpStatus.BAD_REQUEST, msgUtil.getMessage(JoinMsgKey.FAILED_ID.getKey()));

        return ApiResponse.success(msgUtil.getMessage(JoinMsgKey.SUCCUESS_ID.getKey()));
    }

    public ApiResponse<Void> checkDuplicateUserEmail(String userEmail) {

        if ( joinRepository.countByUserEmail(userEmail) > 0 )
            return ApiResponse.error(HttpStatus.BAD_REQUEST, msgUtil.getMessage(JoinMsgKey.FAILED_EMAIL.getKey()));

        return ApiResponse.success(msgUtil.getMessage(JoinMsgKey.SUCCUESS_EMAIL.getKey()));
    }
}
