package com.project.demo.api.account.presentation;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.api.account.application.AccountService;
import com.project.demo.api.account.application.dto.AccountPasswordChangeDTO;
import com.project.demo.api.account.application.dto.AccountPasswordVerifyDTO;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.BaseDTO;
import com.project.demo.common.constant.CommonConstant.SESSION_KEY;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.util.MsgUtil;
import com.project.demo.common.validation.ValidationSequence;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Tag(name = "Account API", description = "내 정보 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountRestController {

    private final AccountService accountService;

    @PostMapping("/account/password/verify")
    @Operation(summary = "비밀번호 일치 확인 API", description = "사용자의 비밀번호를 전달 받아 일치 확인을 진행합니다.")
    public ApiResponse<String> passwordVerify(@Parameter(description = "사용자 비밀번호") @Validated(ValidationSequence.class) @RequestBody AccountPasswordVerifyDTO dto, HttpSession session) {
        ApiResponse<String> response = accountService.passwordVerify(dto);

        if ( response.getStatus() == HttpStatus.OK ) {
            session.setAttribute(SESSION_KEY.ACCOUNT, response.getData());
            session.setAttribute(SESSION_KEY.ACCOUNT_VERIFY, true);
            session.setAttribute(SESSION_KEY.ACCOUNT_TIME, LocalDateTime.now());
        }

        return response;
    }

    @PatchMapping("/account/password/delay")
    @Operation(summary = "비밀번호 다음에 변경 API", description = "비밀번호 변경을 다음에 진행하도록 수정을 진행합니다.")
    public ApiResponse<Void> passwordDelay(BaseDTO dto) {

        return accountService.passwordDelay(dto);
    }

    @PatchMapping("/account/password")
    @Operation(summary = "비밀번호 변경 API", description = "사용자의 비밀번호 변경 정보를 전달 받아 수정을 진행합니다.")
    public ApiResponse<Void> passwordUpdate(@Parameter(description = "비밀번호 변경 진행을 위한 DTO") @Validated(ValidationSequence.class) @RequestBody AccountPasswordChangeDTO dto, HttpSession session) {

        String accountKey = (String) session.getAttribute(SESSION_KEY.ACCOUNT);

        if ( StringUtils.hasText(accountKey) ) {
            return accountService.passwordUpdate(dto);
        } else {
            MsgUtil msgUtil = new MsgUtil();
            return ApiResponse.error(HttpStatus.BAD_REQUEST, msgUtil.getMessage(CommonMsgKey.FAILED_REQUEST.getKey()));
        }
    }
}
