package com.adloveyou.ms.ad.repository;

import com.adloveyou.ms.ad.domain.AdConfiguration;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AdConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdConfigurationRepository extends JpaRepository<AdConfiguration, Long> {

}
