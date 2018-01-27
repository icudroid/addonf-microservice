package com.adloveyou.ms.game.repository;

import com.adloveyou.ms.game.domain.AdGame;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AdGame entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdGameRepository extends JpaRepository<AdGame, Long> {

}
