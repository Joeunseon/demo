package com.project.demo.api.user.infrastructure;

import java.util.List;

import com.project.demo.api.user.application.dto.PasswordResetDTO;
import com.project.demo.api.user.application.dto.UserDetailDTO;
import com.project.demo.api.user.application.dto.UserListDTO;
import com.project.demo.api.user.application.dto.UserRequestDTO;
import com.project.demo.api.user.domain.UserEntity;

public interface UserRepositoryCustom {

    Long countBySearch(UserRequestDTO dto);

    List<UserListDTO> findBySearch(UserRequestDTO dto);

    UserDetailDTO findByUserSeq(Long userSeq);

    Long passwordRest(PasswordResetDTO dto);

    Long updateById(UserEntity entity);

    Long udpateMe(UserEntity entity);
}
