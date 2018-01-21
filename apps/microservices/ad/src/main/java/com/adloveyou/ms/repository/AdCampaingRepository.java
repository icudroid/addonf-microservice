package com.adloveyou.ms.repository;

import com.adloveyou.ms.domain.ad.AdCampaing;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AdCampaing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdCampaingRepository extends JpaRepository<AdCampaing, Long> {

}
