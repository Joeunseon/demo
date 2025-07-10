package com.project.demo.api.code.presentation;

import java.util.Map;

import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.api.code.application.CodeService;
import com.project.demo.api.code.application.dto.CodeCheckDuplicateDTO;
import com.project.demo.api.code.application.dto.CodeCreateDTO;
import com.project.demo.api.code.application.dto.CodeRequestDTO;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.util.MsgUtil;
import com.project.demo.common.validation.ValidationSequence;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Code API", description = "코드 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CodeRestController {

    private final CodeService codeService;

    private final MsgUtil msgUtil;

    @GetMapping("/codes")
    @Operation(summary = "코드 목록 조회 API", description = "검색조건을 전달 받아 코드 목록을 조회합니다.")
    public ApiResponse<Map<String, Object>> findAll(@Parameter(description = "코드 목록 조회 진행을 위한 DTO") CodeRequestDTO dto) {
        
        return codeService.findAll(dto);
    }

    @PostMapping("/code-group")
    @Operation(summary = "그룹 코드 등록 API", description = "등록할 그룹 코드의 정보를 전달 받아 그룹 코드를 등록합니다.")
    public ApiResponse<Integer> createGrp(@Parameter(description = "그룹 코드 등록을 위한 DTO") @Validated(ValidationSequence.class) @RequestBody CodeCreateDTO dto) {

        return codeService.createGrp(dto);
    }

    @PostMapping("/code")
    @Operation(summary = "코드 등록 API", description = "등록할 코드의 정보를 전달 받아 코드를 등록합니다.")
    public ApiResponse<Integer> createCd(@Parameter(description = "코드 등록을 위한 DTO") @Validated(ValidationSequence.class) @RequestBody CodeCreateDTO dto) {
        
        return codeService.createCd(dto);
    }

    @GetMapping("/code/check-duplicate")
    @Operation(summary = "코드 중복체크 API", description = "코드 혹은 코드명을 전달하면 중복 여부를 확인합니다.")
    public ApiResponse<Void> checkDuplicate(@Parameter(description = "중복 확인 진행을 위한 DTO") @Validated(ValidationSequence.class) CodeCheckDuplicateDTO dto) {

        if ( StringUtils.hasText(dto.getCd()) ) 
            return codeService.checkDuplicateCd(dto.getCd(), dto.getCodeType());

        if ( StringUtils.hasText(dto.getCdNm()) )
            return codeService.checkDuplicateCdNm(dto.getCdNm(), dto.getCodeType());

        return ApiResponse.error(msgUtil.getMessage(CommonMsgKey.FAILED_REQUEST.getKey()));
    }
}
