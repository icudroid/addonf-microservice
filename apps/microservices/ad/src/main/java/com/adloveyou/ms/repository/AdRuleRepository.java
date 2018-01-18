package com.adloveyou.ms.repository;

import com.adloveyou.ms.domain.AdRule;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AdRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdRuleRepository extends JpaRepository<AdRule, Long> {

}
