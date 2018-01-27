package com.adloveyou.ms.ad.repository;

import com.adloveyou.ms.ad.domain.Ad;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Ad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

}
