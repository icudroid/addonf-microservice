package com.adloveyou.ms.repository;

import com.adloveyou.ms.domain.media.MediaUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MediaUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MediaUserRepository extends JpaRepository<MediaUser, Long> {

}
