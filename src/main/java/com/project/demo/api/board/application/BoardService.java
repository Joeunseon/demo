package com.project.demo.api.board.application;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.project.demo.api.board.application.dto.BoardRequestDTO;
import com.project.demo.api.board.infrastructure.BoardRepository;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.CommonConstant.MODEL_KEY;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    public ApiResponse<Map<String, Object>> findAll(BoardRequestDTO dto) {

        try {
            Map<String, Object> dataMap = new HashMap<>();

            Long totalCnt = boardRepository.countBySearch(dto);

            if ( totalCnt > 0 )
                dataMap.put(MODEL_KEY.RESULT_LIST, boardRepository.findBySearch(dto));
            
            dataMap.put(MODEL_KEY.TOTAL_CNT, totalCnt);

            dataMap.put(MODEL_KEY.CURRENT_PAGE, dto.getPage());
            dataMap.put(MODEL_KEY.PAGE_SCALE, dto.getPageScale());

            return ApiResponse.success(dataMap);
        } catch (Exception e) {
            log.error(">>>> BoardService::findAll: ", e);
            return ApiResponse.error();
        }
    }
}
