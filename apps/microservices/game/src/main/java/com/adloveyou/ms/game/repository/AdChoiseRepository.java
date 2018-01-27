package com.adloveyou.ms.game.repository;

import com.adloveyou.ms.game.domain.AdChoise;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AdChoise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdChoiseRepository extends JpaRepository<AdChoise, Long> {

}
