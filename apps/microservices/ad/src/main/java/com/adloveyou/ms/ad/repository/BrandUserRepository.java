package com.adloveyou.ms.ad.repository;

import com.adloveyou.ms.ad.domain.BrandUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BrandUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BrandUserRepository extends JpaRepository<BrandUser, Long> {

}
