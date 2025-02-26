package com.project.demo.api.user.application;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.project.demo.api.user.application.dto.SignupRequestDTO;
import com.project.demo.api.user.domain.UserEntity;
import com.project.demo.api.user.infrastructure.SignupRepository;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.CommonMsgKey;
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

    private final BCryptPasswordEncoder passwordEncoder;

    private static final List<String> FORBIDDEN_WORDS = Arrays.asList("admin", "system", "root");

    /**
     * 아이디 중복 체크
     * @param userId
     * @return
     */
    public ApiResponse<Void> checkDuplicateUserId(String userId) {

        String lowerCaseId = userId.toLowerCase();
        if ( FORBIDDEN_WORDS.stream().anyMatch(lowerCaseId::contains) ) 
            return ApiResponse.error(HttpStatus.BAD_REQUEST, msgUtil.getMessage(SignupMsgKey.ID_FORBIDDEN_WORDS.getKey()));
        
        if ( signupRepository.countByUserId(userId) > 0 )
            return ApiResponse.error(HttpStatus.BAD_REQUEST, msgUtil.getMessage(SignupMsgKey.FAILED_ID.getKey()));

        return ApiResponse.success(msgUtil.getMessage(SignupMsgKey.SUCCUESS_ID.getKey()));
    }

    /**
     * 이메일 중복 체크
     * @param userEmail
     * @return
     */
    public ApiResponse<Void> checkDuplicateUserEmail(String userEmail) {

        if ( signupRepository.countByUserEmail(userEmail) > 0 )
            return ApiResponse.error(HttpStatus.BAD_REQUEST, msgUtil.getMessage(SignupMsgKey.FAILED_EMAIL.getKey()));

        return ApiResponse.success(msgUtil.getMessage(SignupMsgKey.SUCCUESS_EMAIL.getKey()));
    }

    /**
     * 비밀번호 ID 포함 여부 확인
     * @param userId
     * @param userPwd
     * @return
     */
    public ApiResponse<Void> checkPwdContainsId(String userId, String userPwd) {

        for (int i = 0; i <= userId.length() -4; i++) {
            String subString = userId.substring(i, i+4);

            if ( StringUtils.hasText(subString) && userPwd.contains(subString) ) 
                return ApiResponse.error(msgUtil.getMessage(SignupMsgKey.PWD_CONTAINS_ID.getKey()));
        }

        return ApiResponse.success();
    }

    /**
     * 회원가입
     * @param dto
     * @return
     */
    public ApiResponse<Void> signup(SignupRequestDTO dto) {

        try {
            ApiResponse<Void> checkId = checkDuplicateUserId(dto.getUserId());
            if ( checkId.getStatus() != HttpStatus.OK )
                return checkId;
            
            ApiResponse<Void> checkPwd = checkPwdContainsId(dto.getUserId(), dto.getUserPwd());
            if ( checkPwd.getStatus() != HttpStatus.OK )
                return checkPwd;
            
            ApiResponse<Void> checkEmail = checkDuplicateUserEmail(dto.getUserEmail());
            if ( checkEmail.getStatus() != HttpStatus.OK ) 
                return checkEmail;

            UserEntity userEntity = dto.toEntity(passwordEncoder.encode(dto.getUserPwd()));
            signupRepository.save(userEntity);
            
            return ApiResponse.success(msgUtil.getMessage(SignupMsgKey.SUCCESS.getKey()));
        } catch (Exception e) {
            log.error(">>>> singup: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }
}
