package com.project.demo.api.account.infrastructure;

import com.project.demo.api.user.domain.UserEntity;

public interface AccountRepositoryCustom {

    Long updatePassowrd(UserEntity entity);
}
