package com.project.demo.api.role.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.demo.api.role.domain.RoleEntity;
import com.project.demo.common.constant.DelYn;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>, RoleRepositoryCustom {

    Integer countByRoleNmAndDelYn(String roleNm, DelYn delYn);

    @Query("SELECT COUNT(r) FROM RoleEntity r WHERE r.roleNm = :roleNm AND r.delYn = :delYn AND r.roleSeq != :roleSeq")
    Integer countByRoleNmAndDelYnAndRoleSeqNot(@Param("roleNm") String roleNm, @Param("delYn") DelYn delYn, @Param("roleSeq") Integer roleSeq);

    List<RoleEntity> findByDelYnOrderByRegDtAsc(DelYn delYn);
}
