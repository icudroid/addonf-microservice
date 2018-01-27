package com.adloveyou.ms.ad.repository;

import com.adloveyou.ms.ad.domain.BidCategoryMedia;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BidCategoryMedia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BidCategoryMediaRepository extends JpaRepository<BidCategoryMedia, Long> {

}
