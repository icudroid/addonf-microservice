package com.adloveyou.ms.ad.repository;

import com.adloveyou.ms.ad.domain.AgencyUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AgencyUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgencyUserRepository extends JpaRepository<AgencyUser, Long> {

}
