package com.project.demo.api.account.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.demo.api.user.domain.UserEntity;

@Repository
public interface AccountRepository extends JpaRepository<UserEntity, Long>, AccountRepositoryCustom {

    UserEntity findByUserSeq(Long userSeq);
}
