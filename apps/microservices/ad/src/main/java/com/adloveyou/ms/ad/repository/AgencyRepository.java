package com.adloveyou.ms.ad.repository;

import com.adloveyou.ms.ad.domain.Agency;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Agency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {

}
