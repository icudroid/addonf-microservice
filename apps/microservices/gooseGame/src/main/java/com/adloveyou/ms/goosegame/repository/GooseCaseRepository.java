package com.adloveyou.ms.goosegame.repository;

import com.adloveyou.ms.goosegame.domain.GooseCase;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GooseCase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GooseCaseRepository extends JpaRepository<GooseCase, Long> {

}
