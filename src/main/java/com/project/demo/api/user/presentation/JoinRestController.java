package com.project.demo.api.user.presentation;

import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.api.user.application.JoinService;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.util.MsgUtil;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class JoinRestController {

    private final JoinService joinService;
    private final MsgUtil msgUtil;

    @GetMapping("/join/check-duplicate")
    public ApiResponse<Void> checkDuplicate(@RequestParam(required = false) @Pattern(regexp = "^[a-zA-Z0-9_-]{5,20}$", message = "{error.join.id_pattern}") String userId,
        @RequestParam(required = false) @Email(message = "{error.join.email_pattern}") @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "{error.join.email_pattern}") String userEmail) {

        if ( StringUtils.hasText(userId) )
            return joinService.checkDuplicateUserId(userId);

        if ( StringUtils.hasText(userEmail) )
            return joinService.checkDuplicateUserEmail(userEmail);

        return ApiResponse.error(msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
    }
}
