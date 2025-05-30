package com.project.demo.api.role.application;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import com.project.demo.api.menu.application.dto.MenuDisplayDTO;
import com.project.demo.api.menu.value.ActiveYn;
import com.project.demo.api.menu.value.MenuType;
import com.project.demo.api.role.application.dto.MenuRoleDTO;
import com.project.demo.api.role.application.dto.MenuRoleTreeDTO;
import com.project.demo.api.role.domain.MenuRoleEntity;
import com.project.demo.api.role.infrastructure.MenuRoleRepository;
import com.project.demo.common.constant.DelYn;
import com.project.demo.common.util.MsgUtil;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.CommonConstant.CACHE_KEY;
import com.project.demo.common.constant.CommonConstant.EXCLUDE_URL;
import com.project.demo.common.constant.CommonConstant.ROLE_KEY;
import com.project.demo.common.constant.CommonMsgKey;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuRoleService {

    private final MenuRoleRepository menuRoleRepository;
    private final AntPathMatcher pathMatcher = new AntPathMatcher(); // URL 패턴 매칭
    private final MsgUtil msgUtil;

    @Cacheable(value = CACHE_KEY.SECURITY, key = "'guestUrls'", unless = "#result == null or #result.isEmpty()")
    public List<String> getGuestAccessibleUrls() {
        log.info(">>> DB에서 GUEST_ROLE 접근 가능 URL 조회");

        return menuRoleRepository.findByRole_RoleSeqAndMenu_ActiveYnAndDelYn(ROLE_KEY.GUEST, ActiveYn.Y, DelYn.N)
                .stream()
                .map(menuRole -> converToWildcardpattern(menuRole.getMenu().getMenuUrl()))
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    @CacheEvict(value = CACHE_KEY.SECURITY, key = "'guestUrls'")
    public void refreshGuestAccessibleUrls() {
        log.info(">>> GUEST_ROLE 접근 가능 URL 캐시 갱신");
    }

    private String converToWildcardpattern(String url) {

        return StringUtils.hasText(url) ? url.replaceAll("\\{[^}]+\\}", "*") : url;
    }

    @Transactional(readOnly = true)
    public boolean hasAccess(Integer roleSeq, String url, String method) {
        
        List<MenuRoleDTO> menuRoles = menuRoleRepository.findByRole_RoleSeqAndMenu_ActiveYnAndDelYn(roleSeq, ActiveYn.Y, DelYn.N)
                                        .stream()
                                        .map(MenuRoleDTO::new)
                                        .filter(menuRoleDTO -> Objects.nonNull(menuRoleDTO.getMenuUrl()))
                                        .distinct()
                                        .collect(Collectors.toList());

        return menuRoles.stream().anyMatch(menuRoleDTO -> 
            pathMatcher.match(menuRoleDTO.getMenuUrl(), url) && (url.contains(EXCLUDE_URL.LOGIN) || isMethodAllowed(menuRoleDTO.getMenuType(), method))
        );
    }

    public boolean isMethodAllowed(MenuType menuType, String method) {
        if ( menuType == null ) 
            return false;
        
        return switch (menuType) {
            case MENU, PAGE, READ -> method.equalsIgnoreCase("GET");
            case TOOL -> method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("GET");
            case UPDATE -> method.equalsIgnoreCase("PATCH");
            case CREATE -> method.equalsIgnoreCase("POST");
            case DELETE -> method.equalsIgnoreCase("DELETE");
            default -> false;
        };
    }

    public List<MenuDisplayDTO> getDisplayMenus(Integer roleSeq) {

        return menuRoleRepository.findDisplayMenus(roleSeq).stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public ApiResponse<List<MenuRoleTreeDTO>> findMenusById(Integer roleSeq) {

        try {

            List<MenuRoleTreeDTO> list = buildMenuTree(menuRoleRepository.findMenusByRole(roleSeq));
            
            return ApiResponse.success(list);
        } catch (Exception e) {
            log.error(">>>> MenuRoleService::findMenusById: ", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, msgUtil.getMessage(CommonMsgKey.FAILED.getKey()));
        }
    }

    private List<MenuRoleTreeDTO> buildMenuTree(List<MenuRoleEntity> flatList) {

        Map<Long, MenuRoleTreeDTO> dtoMap = new LinkedHashMap<>();
        List<MenuRoleTreeDTO> roots = new ArrayList<>();

        // 1차: flat DTO -> Tree DTO 변환
        for (MenuRoleEntity dto : flatList) {
            MenuRoleTreeDTO treeDTO = new MenuRoleTreeDTO(dto);
            dtoMap.put(treeDTO.getMenuSeq(), treeDTO);
        }

        // 2차: 계층 연결
        for (MenuRoleTreeDTO dto : dtoMap.values()) {
            Long parentSeq = dto.getParentSeq();
            if ( parentSeq != null && dtoMap.containsKey(parentSeq) ) {
                dtoMap.get(parentSeq).getChildren().add(dto);
            } else {
                roots.add(dto);
            }
        }

        return roots;
    }
}
