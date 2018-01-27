package com.adloveyou.uaa.repository;

import com.adloveyou.uaa.domain.Role;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Role entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select distinct role from Role role left join fetch role.permissions")
    List<Role> findAllWithEagerRelationships();

    @Query("select role from Role role left join fetch role.permissions where role.id =:id")
    Role findOneWithEagerRelationships(@Param("id") Long id);

}
