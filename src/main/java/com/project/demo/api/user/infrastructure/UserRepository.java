package com.project.demo.api.user.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.demo.api.user.domain.UserEntity;
import com.project.demo.api.user.value.ActiveYn;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserIdAndActiveYn(String userId, ActiveYn activeYn);
}
