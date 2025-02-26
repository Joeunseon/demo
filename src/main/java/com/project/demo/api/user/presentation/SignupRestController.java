package com.project.demo.api.user.presentation;

import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.api.user.application.SignupService;
import com.project.demo.api.user.application.dto.SignupRequestDTO;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.util.MsgUtil;
import com.project.demo.common.validation.ValidationSequence;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SignupRestController {

    private final SignupService signupService;
    private final MsgUtil msgUtil;

    @GetMapping("/signup/check-duplicate")
    public ApiResponse<Void> checkDuplicate(@RequestParam(required = false) @Pattern(regexp = "^[a-zA-Z0-9_-]{5,20}$", message = "{error.signup.id_pattern}") String userId,
        @RequestParam(required = false) @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "{error.signup.email_pattern}") String userEmail) {

        if ( StringUtils.hasText(userId) )
            return signupService.checkDuplicateUserId(userId);

        if ( StringUtils.hasText(userEmail) )
            return signupService.checkDuplicateUserEmail(userEmail);

        return ApiResponse.error(msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
    }

    @PostMapping("/signup")
    public ApiResponse<Void> signup(@Validated(ValidationSequence.class) @RequestBody SignupRequestDTO dto) {

        return signupService.signup(dto);
    }
}
