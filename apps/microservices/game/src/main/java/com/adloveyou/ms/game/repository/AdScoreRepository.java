package com.adloveyou.ms.game.repository;

import com.adloveyou.ms.game.domain.AdScore;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AdScore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdScoreRepository extends JpaRepository<AdScore, Long> {

}
