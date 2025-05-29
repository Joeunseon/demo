package com.project.demo.api.account.application;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.demo.api.account.application.dto.AccountPasswordChangeDTO;
import com.project.demo.api.account.infrastructure.AccountRepository;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.util.MsgUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final MsgUtil msgUtil;

    @Transactional(readOnly = false)
    public ApiResponse<Void> passwordUpdate(AccountPasswordChangeDTO dto) {

        try {
            if ( dto.getUserSessionDTO() == null || dto.getUserSessionDTO().getUserSeq() == null ) {
                return ApiResponse.error(HttpStatus.FORBIDDEN, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));
            }

            // 기존 비밀번호 일치 확인 추가 필요

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            // 비밀번호 변경
            accountRepository.updatePassowrd(dto.toEntity(dto.getUserSessionDTO().getUserSeq(), passwordEncoder.encode(dto.getNewPwd())));

            return ApiResponse.success();
        } catch (Exception e) {
            log.error(">>>> AccountService::passwordUpdate: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }
}
