package com.project.demo.api.menu.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import com.project.demo.api.menu.application.dto.MenuChildrenDTO;
import com.project.demo.api.menu.application.dto.MenuDetailDTO;
import com.project.demo.api.menu.application.dto.MenuRequestDTO;
import com.project.demo.api.menu.application.dto.MenuTreeDTO;
import com.project.demo.api.menu.domain.MenuEntity;
import com.project.demo.api.menu.infrastructure.MenuRepository;
import com.project.demo.api.menu.value.ActiveYn;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.constant.CommonConstant.MODEL_KEY;
import com.project.demo.common.util.MsgUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuService {

    private final MenuRepository menuRepository;
    private final AntPathMatcher pathMatcher = new AntPathMatcher(); // URL 패턴 매칭

    private final MsgUtil msgUtil;

    public boolean isMenuExist(String url) {

        List<String> menuUrlPatterns = menuRepository.findByActiveYn(ActiveYn.Y)
                                        .stream()
                                        .map(menu -> menu.getMenuUrl())
                                        .filter(Objects::nonNull)
                                        .distinct()
                                        .collect(Collectors.toList());

        return menuUrlPatterns.stream().anyMatch(pattern -> pathMatcher.match(pattern, url));
    }

    public ApiResponse<Map<String, Object>> findAll(MenuRequestDTO dto) {

        try {
            Map<String, Object> dataMap = new HashMap<>();

            Long totalCnt = menuRepository.countBySearch(dto);

            if ( totalCnt > 0 )
                dataMap.put(MODEL_KEY.RESULT_LIST, menuRepository.findBySearch(dto));

            dataMap.put(MODEL_KEY.TOTAL_CNT, totalCnt);

            dataMap.put(MODEL_KEY.CURRENT_PAGE, dto.getPage());
            dataMap.put(MODEL_KEY.PAGE_SCALE, dto.getPageScale());

            return ApiResponse.success(dataMap);
        } catch (Exception e) {
            log.error(">>>> MenuService::findAll: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    public ApiResponse<MenuDetailDTO> findById(Long menuSeq) {

        try {
            Optional<MenuEntity> menu = menuRepository.findById(menuSeq);

            return menu.map(info -> ApiResponse.success(MenuDetailDTO.of(info)))
                        .orElse(ApiResponse.success());
        } catch (Exception e) {
            log.error(">>>> MenuService::findById: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    public ApiResponse<List<MenuChildrenDTO>> findAllByParentId(Long parentSeq) {

        try {
            List<MenuChildrenDTO> level3 = getChildren(parentSeq);

            if ( !level3.isEmpty() ) {
                for (MenuChildrenDTO info : level3) {
                    List<MenuChildrenDTO> level4 = getChildren(info.getMenuSeq());
                    
                    if ( !level4.isEmpty() ) {
                        info.ofChildren(level4);
                    }
                }
            }

            return ApiResponse.success(level3);
        } catch (Exception e) {
            log.error(">>>> MenuService::findAllByParentId: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    private List<MenuChildrenDTO> getChildren(Long parentSeq) {

        return menuRepository.findByParentSeqOrderByMenuOrderAsc(parentSeq)
                                .stream()
                                .map(MenuChildrenDTO::of)
                                .filter(Objects::nonNull)
                                .collect(Collectors.collectingAndThen(
                                    Collectors.toCollection(LinkedHashSet::new),
                                    ArrayList::new
                                ));
    }

    public ApiResponse<List<MenuTreeDTO>> findAllAsTree() {

        try {
            List<MenuEntity> list = menuRepository.findAllForTreeView();

            if ( !list.isEmpty() ) {
                Map<Long, MenuTreeDTO> map = new HashMap<>();

                for (MenuEntity menu : list ) {
                    MenuTreeDTO dto = MenuTreeDTO.of(menu);
                    map.put(menu.getMenuSeq(), dto);
                }

                List<MenuTreeDTO> roots = new ArrayList<>();
                
                // 계층구조로 변환
                for (MenuEntity menu : list) {
                    MenuTreeDTO dto = map.get(menu.getMenuSeq());
            
                    if (menu.getParentSeq() == null) {
                        roots.add(dto);
                    } else {
                        if ( menu.getMenuLevel() < 4 ) {
                            MenuTreeDTO parent = map.get(menu.getParentSeq());
                            if (parent != null) {
                                if (parent.getChildren() == null) {
                                    parent.ofChildren(new ArrayList<>());
                                }
                                parent.getChildren().add(dto);
                            }
                        }
                    }
                }

                return ApiResponse.success(roots);
            }

            return ApiResponse.success();
        } catch (Exception e) {
            log.error(">>>> MenuService::findAllAsTree: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }
}
