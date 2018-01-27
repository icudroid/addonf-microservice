package com.adloveyou.ms.goosegame.repository;

import com.adloveyou.ms.goosegame.domain.GooseLevel;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GooseLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GooseLevelRepository extends JpaRepository<GooseLevel, Long> {

}
