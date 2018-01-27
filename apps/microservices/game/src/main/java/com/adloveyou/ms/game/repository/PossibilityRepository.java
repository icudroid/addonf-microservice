package com.adloveyou.ms.game.repository;

import com.adloveyou.ms.game.domain.Possibility;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Possibility entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PossibilityRepository extends JpaRepository<Possibility, Long> {

}
