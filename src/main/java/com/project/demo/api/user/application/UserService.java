package com.project.demo.api.user.application;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.demo.api.user.application.dto.UserRequestDTO;
import com.project.demo.api.user.infrastructure.UserRepository;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.CommonConstant.MODEL_KEY;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.constant.LoginMsgKey;
import com.project.demo.common.util.MsgUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final MsgUtil msgUtil;

    @Transactional
    public void updateLastLoginDt(Long userSeq) {
        
        Integer result = userRepository.updateLastLoginTime(userSeq);

        if ( result > 0 ) {
            log.info(msgUtil.getMessage(LoginMsgKey.SUCCUESS_LAST_LOGIN.getKey(), userSeq));
        } else {
            log.warn(msgUtil.getMessage(LoginMsgKey.FAILED_LAST_LOGIN.getKey(), userSeq));
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
}
