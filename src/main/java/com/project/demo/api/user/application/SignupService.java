package com.project.demo.api.user.application;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.demo.api.user.infrastructure.SignupRepository;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.SignupMsgKey;
import com.project.demo.common.util.MsgUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignupService {

    private final SignupRepository signupRepository;
    private final MsgUtil msgUtil;

    private static final List<String> FORBIDDEN_WORDS = Arrays.asList("admin", "system", "root");

    public ApiResponse<Void> checkDuplicateUserId(String userId) {

        String lowerCaseId = userId.toLowerCase();
        if ( FORBIDDEN_WORDS.stream().anyMatch(lowerCaseId::contains) ) 
            return ApiResponse.error(HttpStatus.BAD_REQUEST, msgUtil.getMessage(SignupMsgKey.ID_FORBIDDEN_WORDS.getKey()));
        
        if ( signupRepository.countByUserId(userId) > 0 )
            return ApiResponse.error(HttpStatus.BAD_REQUEST, msgUtil.getMessage(SignupMsgKey.FAILED_ID.getKey()));

        return ApiResponse.success(msgUtil.getMessage(SignupMsgKey.SUCCUESS_ID.getKey()));
    }

    public ApiResponse<Void> checkDuplicateUserEmail(String userEmail) {

        if ( signupRepository.countByUserEmail(userEmail) > 0 )
            return ApiResponse.error(HttpStatus.BAD_REQUEST, msgUtil.getMessage(SignupMsgKey.FAILED_EMAIL.getKey()));

        return ApiResponse.success(msgUtil.getMessage(SignupMsgKey.SUCCUESS_EMAIL.getKey()));
    }
}
