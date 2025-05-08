package com.project.demo.api.common.application;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.project.demo.api.common.application.dto.SelectBoxDTO;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.util.MsgUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnumService {

    private final MsgUtil msgUtil;

    public ApiResponse<List<SelectBoxDTO>> getEnum(String enumFillPath) {
        
        try {
            if ( StringUtils.hasText(enumFillPath) )
                enumFillPath = "com.project.demo." + enumFillPath;
            
            // enum 클래스 로딩
            Class<?> enumClass = Class.forName(enumFillPath);

            // enum 아닌 경우 에러 처리
            if ( !enumClass.isEnum() )
                return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, enumFillPath + " is not an enum type.");

            return ApiResponse.success(Arrays.stream(enumClass.getEnumConstants())
                                        .map(enumValue -> {
                                            try {
                                                var descriptionField = enumValue.getClass().getDeclaredField("description");
                                                descriptionField.setAccessible(true);
                                                String description = (String) descriptionField.get(enumValue);

                                                return new SelectBoxDTO(enumValue.toString(), description);
                                            } catch (NoSuchFieldException | IllegalAccessException e) {
                                                // description 필드가 없는 경우 기본 toString() 사용
                                                return new SelectBoxDTO(enumValue.toString(), enumValue.toString());
                                            }
                                        })
                                        .collect(Collectors.toList()));
        } catch (Exception e) {
            log.error(">>>> EnumService::getEnum: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }
}
