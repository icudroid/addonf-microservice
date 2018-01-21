package com.adloveyou.ms.repository;

import com.adloveyou.ms.domain.ad.CustomerTarget;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CustomerTarget entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerTargetRepository extends JpaRepository<CustomerTarget, Long> {

}
