package com.project.demo.api.user.presentation;

import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.api.user.application.SignupService;
import com.project.demo.api.user.application.dto.SignupCheckDuplicateDTO;
import com.project.demo.api.user.application.dto.SignupRequestDTO;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.util.MsgUtil;
import com.project.demo.common.validation.ValidationSequence;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Signup API", description = "회원가입 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SignupRestController {

    private final SignupService signupService;
    private final MsgUtil msgUtil;

    @GetMapping("/signup/check-duplicate")
    @Operation(summary = "아이디 혹은 이메일 중복 확인 API", description = "아이디 혹은 이메일을 전달하면 중복 여부를 확인합니다.")
    public ApiResponse<Void> checkDuplicate(@Parameter(description = "중복 확인 진행을 위한 DTO") SignupCheckDuplicateDTO dto) {

        if ( StringUtils.hasText(dto.getUserId()) )
            return signupService.checkDuplicateUserId(dto.getUserId());

        if ( StringUtils.hasText(dto.getUserEmail()) )
            return signupService.checkDuplicateUserEmail(dto.getUserEmail());

        return ApiResponse.error(msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입 API", description = "사용자의 정보를 입력받아 회원가입을 진행합니다.")
    public ApiResponse<Void> signup(@Parameter(description = "회원가입 진행을 위한 DTO") @Validated(ValidationSequence.class) @RequestBody SignupRequestDTO dto) {

        return signupService.signup(dto);
    }
}
