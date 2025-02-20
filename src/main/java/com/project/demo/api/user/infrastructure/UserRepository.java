package com.project.demo.api.user.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.demo.api.user.domain.UserEntity;
import com.project.demo.api.user.value.ActiveYn;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, UserRepositoryCustom {

    Optional<UserEntity> findByUserIdAndActiveYn(String userId, ActiveYn activeYn);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.lastLoginDt = CURRENT_TIMESTAMP WHERE u.userSeq = :userSeq")
    Integer updateLastLoginTime(@Param("userSeq") Long userSeq);
}
