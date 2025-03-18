package com.project.demo.api.board.application;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.demo.api.board.application.dto.BoardCreateDTO;
import com.project.demo.api.board.application.dto.BoardDetailDTO;
import com.project.demo.api.board.application.dto.BoardRequestDTO;
import com.project.demo.api.board.domain.BoardEntity;
import com.project.demo.api.board.infrastructure.BoardRepository;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.BaseDTO;
import com.project.demo.common.constant.CommonConstant.MODEL_KEY;
import com.project.demo.common.constant.CommonConstant.ROLE_KEY;
import com.project.demo.common.constant.CommonConstant.SESSION_KEY;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.util.MsgUtil;
import com.project.demo.common.util.SessionUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final MsgUtil msgUtil;

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
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));
        }
    }

    public ApiResponse<Map<String, Object>> findById(Long boardSeq, BaseDTO dto) {

        try {
            Map<String, Object> data = new HashMap<>();
            BoardDetailDTO info = boardRepository.findDetailById(boardSeq);

            if ( info == null )
                return ApiResponse.error(HttpStatus.NO_CONTENT, null, msgUtil.getMessage(CommonMsgKey.FAILED_REQUEST.getKey()));

            data.put(MODEL_KEY.DATA, info);

            boolean isOwnerOrAdmin = false;
            if ( dto != null && dto.getUserSessionDTO() != null ) { 
                isOwnerOrAdmin = ( info.getRegSeq() == dto.getUserSessionDTO().getUserSeq() || dto.getUserSessionDTO().getRoleSeq() == ROLE_KEY.ADMIN || dto.getUserSessionDTO().getRoleSeq() == ROLE_KEY.SYSTEM );
            }
            data.put(MODEL_KEY.EDITABLE, isOwnerOrAdmin);

            return ApiResponse.success(data);
        } catch (Exception e) {
            log.error(">>>> BoardService::findById: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));
        }
    }

    @Transactional(readOnly = false)
    public ApiResponse<Void> updateViewCnt(Long boardSeq, HttpSession session) {

        try {
            Set<Long> viewedBoards = SessionUtil.getSessionSet(session, SESSION_KEY.BOARD, Long.class);
            
            boolean isFirstView = viewedBoards.add(boardSeq);
            session.setAttribute(SESSION_KEY.BOARD, viewedBoards);
            if ( isFirstView ) { 
                boardRepository.updateViewCnt(boardSeq);
            }

            return ApiResponse.success();
        } catch (Exception e) {
            log.error(">>>> BoardService::updateViewCnt: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    public ApiResponse<Long> create(BoardCreateDTO dto) {

        try {
            if ( dto.getUserSessionDTO() == null || dto.getUserSessionDTO().getUserSeq() == null ) {
                return ApiResponse.error(HttpStatus.FORBIDDEN, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));
            }

            BoardEntity entity = boardRepository.save(dto.toEntity(dto.getUserSessionDTO().getUserSeq()));

            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCUESS.getKey()), entity.getBoardSeq());
        } catch (Exception e) {
            log.error(">>>> BoardService::create: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    @Transactional(readOnly = false)
    public ApiResponse<Void> delete(Long boardSeq, BaseDTO dto) {

        try {
            if ( dto.getUserSessionDTO() == null || dto.getUserSessionDTO().getUserSeq() == null ) {
                return ApiResponse.error(HttpStatus.FORBIDDEN, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));
            }

            ApiResponse<Map<String, Object>> info = findById(boardSeq, dto);

            if ( info.getData() == null )
                return ApiResponse.error(info.getStatus(), info.getMessage());

            BoardDetailDTO boardDetail = (BoardDetailDTO) info.getData().get(MODEL_KEY.DATA);
            
            if ( (boardDetail != null && boardDetail.getRegSeq() != dto.getUserSessionDTO().getUserSeq()) || (dto.getUserSessionDTO().getRoleSeq() != ROLE_KEY.SYSTEM && dto.getUserSessionDTO().getRoleSeq() != ROLE_KEY.ADMIN) )
                return ApiResponse.error(HttpStatus.FORBIDDEN, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));

            // 논리 삭제
            boardRepository.softDelete(boardSeq, dto.getUserSessionDTO().getUserSeq());

            return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCUESS.getKey()));
        } catch (Exception e) {
            log.error(">>>> BoardService::delete: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }
}
