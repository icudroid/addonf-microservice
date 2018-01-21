package com.adloveyou.ms.repository;

import com.adloveyou.ms.domain.agency.AgencyUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AgencyUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgencyUserRepository extends JpaRepository<AgencyUser, Long> {

}
