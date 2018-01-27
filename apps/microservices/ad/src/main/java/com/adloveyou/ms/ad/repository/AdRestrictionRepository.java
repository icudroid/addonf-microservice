package com.adloveyou.ms.ad.repository;

import com.adloveyou.ms.ad.domain.AdRestriction;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AdRestriction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdRestrictionRepository extends JpaRepository<AdRestriction, Long> {

}
