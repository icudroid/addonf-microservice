package com.adloveyou.uaa.repository;

import com.adloveyou.uaa.domain.Profile;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Profile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Query("select distinct profile from Profile profile left join fetch profile.roles")
    List<Profile> findAllWithEagerRelationships();

    @Query("select profile from Profile profile left join fetch profile.roles where profile.id =:id")
    Profile findOneWithEagerRelationships(@Param("id") Long id);

    Profile findByName(String profileName);
}
