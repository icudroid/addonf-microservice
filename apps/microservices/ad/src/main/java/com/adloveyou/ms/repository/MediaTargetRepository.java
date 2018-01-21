package com.adloveyou.ms.repository;

import com.adloveyou.ms.domain.ad.MediaTarget;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MediaTarget entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MediaTargetRepository extends JpaRepository<MediaTarget, Long> {

}
