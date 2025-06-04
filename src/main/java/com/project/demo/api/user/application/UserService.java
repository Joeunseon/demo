package com.project.demo.api.user.application;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.project.demo.api.user.application.dto.PasswordResetDTO;
import com.project.demo.api.user.application.dto.UserCreateDTO;
import com.project.demo.api.user.application.dto.UserDetailDTO;
import com.project.demo.api.user.application.dto.UserRequestDTO;
import com.project.demo.api.user.application.dto.UserUpdateDTO;
import com.project.demo.api.user.domain.UserEntity;
import com.project.demo.api.user.infrastructure.UserRepository;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.BaseDTO;
import com.project.demo.common.constant.CommonConstant.MODEL_KEY;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.constant.AuthMsgKey;
import com.project.demo.common.util.MsgUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final SignupService signupService;
    private final UserRepository userRepository;
    private final MsgUtil msgUtil;

    @Transactional
    public void updateLastLoginDt(Long userSeq) {
        
        Integer result = userRepository.updateLastLoginTime(userSeq);

        if ( result > 0 ) {
            log.info(msgUtil.getMessage(AuthMsgKey.SUCCUESS_LAST_LOGIN.getKey(), userSeq));
        } else {
            log.warn(msgUtil.getMessage(AuthMsgKey.FAILED_LAST_LOGIN.getKey(), userSeq));
        }
    }

    public ApiResponse<Map<String, Object>> findAll(UserRequestDTO dto) {

        try {
            Map<String, Object> dataMap = new HashMap<>();

            Long totalCnt = userRepository.countBySearch(dto);

            if ( totalCnt > 0 )
                dataMap.put(MODEL_KEY.RESULT_LIST, userRepository.findBySearch(dto));

            dataMap.put(MODEL_KEY.TOTAL_CNT, totalCnt);

            dataMap.put(MODEL_KEY.CURRENT_PAGE, dto.getPage());
            dataMap.put(MODEL_KEY.PAGE_SCALE, dto.getPageScale());

            return ApiResponse.success(dataMap);
        } catch (Exception e) {
            log.error(">>>> UserService::findAll: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    public ApiResponse<UserDetailDTO> findById(Long userSeq) {

        try {
            
            return ApiResponse.success(userRepository.findByUserSeq(userSeq));
        } catch (Exception e) {
            log.error(">>>> UserService::findById: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    @Transactional(readOnly = false)
    public ApiResponse<Void> passwordInit(Long userSeq, BaseDTO dto) {

        try {
            if ( dto.getUserSessionDTO() == null || dto.getUserSessionDTO().getUserSeq() == null ) {
                return ApiResponse.error(HttpStatus.FORBIDDEN, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));
            }

            ApiResponse<UserDetailDTO> info = findById(userSeq);

            if ( info.getStatus() != HttpStatus.OK || info.getData() == null || !StringUtils.hasText(info.getData().getUserId()) )
                return ApiResponse.error(info.getStatus(), msgUtil.getMessage(CommonMsgKey.FAILED_REQUEST.getKey()));

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            PasswordResetDTO resetDTO = PasswordResetDTO.builder()
                                                        .userSeq(userSeq)
                                                        .updSeq(dto.getUserSessionDTO().getUserSeq())
                                                        .encodePwd(passwordEncoder.encode(info.getData().getUserId()))
                                                        .build();
            
            // 비밀번호 초기화 진행
            userRepository.passwordRest(resetDTO);
            
            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCUESS.getKey()));
        } catch (Exception e) {
            log.error(">>>> UserService::passwordInit: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    @Transactional(readOnly = false)
    public ApiResponse<Long> update(UserUpdateDTO dto) {

        try {
            if ( dto.getUserSessionDTO() == null || dto.getUserSessionDTO().getUserSeq() == null ) {
                return ApiResponse.error(HttpStatus.FORBIDDEN, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));
            }

            // 사용자 수정
            userRepository.updateById(dto.toEntity(dto.getUserSessionDTO().getUserSeq()));
            
            return ApiResponse.success(dto.getUserSeq());
        } catch (Exception e) {
            log.error(">>>> UserService::update: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    @Transactional(readOnly = false)
    public ApiResponse<Long> create(UserCreateDTO dto) {

        try {
            if ( dto.getUserSessionDTO() == null || dto.getUserSessionDTO().getUserSeq() == null ) {
                return ApiResponse.error(HttpStatus.FORBIDDEN, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));
            }

            // id check
            ApiResponse<Void> checkId = signupService.checkDuplicateUserId(dto.getUserId());
            if ( checkId.getStatus() != HttpStatus.OK )
                return ApiResponse.error(checkId.getStatus(), checkId.getMessage());
            
            // E-mail check
            if ( StringUtils.hasText(dto.getUserEmail()) ) {
                ApiResponse<Void> checkEmail = signupService.checkDuplicateUserEmail(dto.getUserEmail());
                if ( checkEmail.getStatus() != HttpStatus.OK ) 
                    return ApiResponse.error(checkEmail.getStatus(), checkEmail.getMessage());
            }

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            UserEntity entity = userRepository.save(dto.toEntity(passwordEncoder.encode(dto.getUserPwd())));

            return ApiResponse.success(entity.getUserSeq());
        } catch (Exception e) {
            log.error(">>>> UserService::create: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    public ApiResponse<UserDetailDTO> findByMe(BaseDTO dto) {

        try {
            if ( dto.getUserSessionDTO() == null || dto.getUserSessionDTO().getUserSeq() == null ) {
                return ApiResponse.error(HttpStatus.FORBIDDEN, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));
            }
            
            return findById(dto.getUserSessionDTO().getUserSeq());
        } catch (Exception e) {
            log.error(">>>> UserService::findByMe: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }
}
