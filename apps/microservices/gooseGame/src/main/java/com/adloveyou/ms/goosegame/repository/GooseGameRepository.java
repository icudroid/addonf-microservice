package com.adloveyou.ms.goosegame.repository;

import com.adloveyou.ms.goosegame.domain.GooseGame;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GooseGame entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GooseGameRepository extends JpaRepository<GooseGame, Long> {

}
