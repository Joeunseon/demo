package com.project.demo.api.user.infrastructure;

import java.util.List;

import com.project.demo.api.user.application.dto.UserListDTO;
import com.project.demo.api.user.application.dto.UserRequestDTO;

public interface UserRepositoryCustom {

    Long countBySearch(UserRequestDTO dto);

    List<UserListDTO> findBySearch(UserRequestDTO dto);
}
