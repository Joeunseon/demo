package com.project.demo.api.user.presentation;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.api.user.application.UserService;
import com.project.demo.api.user.application.dto.MyUserUpdateDTO;
import com.project.demo.api.user.application.dto.UserCreateDTO;
import com.project.demo.api.user.application.dto.UserDetailDTO;
import com.project.demo.api.user.application.dto.UserRequestDTO;
import com.project.demo.api.user.application.dto.UserUpdateDTO;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.BaseDTO;
import com.project.demo.common.constant.CommonConstant.ACCOUNT_KEY;
import com.project.demo.common.constant.CommonConstant.SESSION_KEY;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.util.MsgUtil;
import com.project.demo.common.validation.ValidationSequence;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@Tag(name = "User API", description = "사용자 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserRestController {

    private final UserService userService;

    @GetMapping("/users")
    @Operation(summary = "사용자 목록 조회 API", description = "검색조건을 전달 받아 사용자 목록을 조회합니다.")
    public ApiResponse<Map<String, Object>> findAll(@Parameter(description = "사용자 목록 조회 진행을 위한 DTO") UserRequestDTO dto) {

        return userService.findAll(dto);
    }

    @GetMapping("/user/{userSeq}")
    @Operation(summary = "사용자 상세 조회 API", description = "사용자 SEQ을 전달 받아 사용자 상세를 조회합니다.")
    public ApiResponse<UserDetailDTO> findById(@Parameter(description = "조회할 사용자 SEQ") @PathVariable("userSeq") @Min(value = 0, message = "{error.request}") Long userSeq) {

        return userService.findById(userSeq);
    }

    @PatchMapping("/user/{userSeq}/password/init")
    @Operation(summary = "사용자 비밀번호 초기화 API (관리자)", description = "사용자 SEQ을 전달 받아 사용자 비밀번호 초기화를 합니다.")
    public ApiResponse<Void> passwordInit(@Parameter(description = "초기화할 사용자 SEQ") @PathVariable("userSeq") @Min(value = 0, message = "{error.request}") Long userSeq, BaseDTO baseDTO) {

        return userService.passwordInit(userSeq, baseDTO);
    }

    @PatchMapping("/user")
    @Operation(summary = "사용자 수정 API", description = "수정할 사용자의 정보를 전달 받아 수정을 진행합니다.")
    public ApiResponse<Long> update(@Parameter(description = "사용자 수정을 위한 DTO") @Validated(ValidationSequence.class) @RequestBody UserUpdateDTO dto) {

        return userService.update(dto);
    }

    @PostMapping("/user")
    @Operation(summary = "사용자 등록 API", description = "등록할 사용자의 정보를 전달 받아 수정을 진행합니다.")
    public ApiResponse<Long> create(@Parameter(description = "사용자 등록을 위한 DTO") @Validated(ValidationSequence.class) @RequestBody UserCreateDTO dto) {

        return userService.create(dto);
    }

    @GetMapping("/user/me")
    @Operation(summary = "내 정보 상세 조회 API (마이페이지)", description = "내 정보 상세를 조회합니다.")
    public ApiResponse<UserDetailDTO> findByMe(@Parameter(description = "사용자 기본 DTO") BaseDTO dto, HttpSession session) {

        Boolean verified = (Boolean) session.getAttribute(SESSION_KEY.ACCOUNT_VERIFY);
        LocalDateTime verifyTime = (LocalDateTime) session.getAttribute(SESSION_KEY.ACCOUNT_TIME);

        if ( verified == null || !verified || verifyTime == null || verifyTime.isBefore(LocalDateTime.now().minusMinutes(10)) ) {
            MsgUtil msgUtil = new MsgUtil();
            return ApiResponse.error(HttpStatus.BAD_REQUEST, msgUtil.getMessage(CommonMsgKey.FAILED_REQUEST.getKey()));
        }

        return userService.findByMe(dto);
    }

    @PatchMapping("/user/me")
    @Operation(summary = "내 정보 수정 API", description = "내 정보 수정을 진행합니다.")
    public ApiResponse<Void> updateMe(@Parameter(description = "내 정보 수정을 위한 DTO") @Validated(ValidationSequence.class) @RequestBody MyUserUpdateDTO dto, HttpSession session) {

        String accountKey = (String) session.getAttribute(SESSION_KEY.ACCOUNT);

        if ( accountKey.equals(ACCOUNT_KEY.ACCOUNT_VERIFY) ) {
            return userService.updateMe(dto);
        }

        MsgUtil msgUtil = new MsgUtil();
        return ApiResponse.error(HttpStatus.BAD_REQUEST, msgUtil.getMessage(CommonMsgKey.FAILED_REQUEST.getKey()));
    }
}
