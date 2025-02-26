package com.project.demo.api.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.demo.api.user.domain.UserEntity;

@Repository
public interface JoinRepository extends JpaRepository<UserEntity, Long> {

    Integer countByUserId(String userId);

    Integer countByUserEmail(String userEmail);
}
