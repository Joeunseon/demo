package com.project.demo.api.account.application;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.demo.api.account.application.dto.AccountPasswordChangeDTO;
import com.project.demo.api.account.application.dto.AccountPasswordVerifyDTO;
import com.project.demo.api.account.infrastructure.AccountRepository;
import com.project.demo.api.user.domain.UserEntity;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.BaseDTO;
import com.project.demo.common.constant.AuthMsgKey;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.exception.CustomException;
import com.project.demo.common.constant.CommonConstant.ACCOUNT_KEY;
import com.project.demo.common.util.MsgUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final MsgUtil msgUtil;

    public ApiResponse<String> passwordVerify(AccountPasswordVerifyDTO dto) {
        
        try {
            if ( dto.getUserSessionDTO() == null || dto.getUserSessionDTO().getUserSeq() == null ) {
                return ApiResponse.error(HttpStatus.FORBIDDEN, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));
            }

            return passwordMatch(dto.getUserPwd(), dto.getUserSessionDTO().getUserSeq());
        } catch (Exception e) {
            log.error(">>>> AccountService::passwordVerify: ", e);
            throw new CustomException(msgUtil.getMessage(CommonMsgKey.FAILED.getKey()), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional(readOnly = false)
    public ApiResponse<Void> passwordUpdate(AccountPasswordChangeDTO dto) {

        try {
            if ( dto.getUserSessionDTO() == null || dto.getUserSessionDTO().getUserSeq() == null ) {
                return ApiResponse.error(HttpStatus.FORBIDDEN, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));
            }

            // 비밀번호 일치 확인
            ApiResponse<String> verify = passwordMatch(dto.getUserPwd(), dto.getUserSessionDTO().getUserSeq());
            if ( verify.getStatus() != HttpStatus.OK )
                return ApiResponse.error(verify.getStatus(), verify.getMessage());

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            // 비밀번호 변경
            accountRepository.updatePassowrd(dto.toEntity(dto.getUserSessionDTO().getUserSeq(), passwordEncoder.encode(dto.getNewPwd())));

            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCUESS.getKey()));
        } catch (Exception e) {
            log.error(">>>> AccountService::passwordUpdate: ", e);
            throw new CustomException(msgUtil.getMessage(CommonMsgKey.FAILED.getKey()), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional(readOnly = false)
    public ApiResponse<Void> passwordDelay(BaseDTO dto) {

        try {
            if ( dto.getUserSessionDTO() == null || dto.getUserSessionDTO().getUserSeq() == null ) {
                return ApiResponse.error(HttpStatus.FORBIDDEN, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));
            }

            // 비밀번호 변경 시간 수정
            accountRepository.updatePasswordDelay(dto.getUserSessionDTO().getUserSeq());
            
            return ApiResponse.success();
        } catch (Exception e) {
            log.error(">>>> AccountService::passwordDelay: ", e);
            throw new CustomException(msgUtil.getMessage(CommonMsgKey.FAILED.getKey()), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private ApiResponse<String> passwordMatch(String userPwd, Long userSeq) {

        try {
            
            UserEntity userEntity = accountRepository.findByUserSeq(userSeq);

            if ( userEntity == null )
                return ApiResponse.error(msgUtil.getMessage(AuthMsgKey.USER_NOT_FOUND.getKey()));

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if ( passwordEncoder.matches(userPwd, userEntity.getUserPwd()) )
                return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCUESS.getKey()), ACCOUNT_KEY.ACCOUNT_VERIFY);
            
            return ApiResponse.error(msgUtil.getMessage(AuthMsgKey.BAD_CREDENTIALS.getKey()));
        } catch (Exception e) {
            log.error(">>>> AccountService::passwordMatch: ", e);
            throw new CustomException(msgUtil.getMessage(CommonMsgKey.FAILED.getKey()), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}
