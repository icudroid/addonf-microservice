package com.adloveyou.ms.repository;

import com.adloveyou.ms.domain.BrandUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BrandUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BrandUserRepository extends JpaRepository<BrandUser, Long> {

}
