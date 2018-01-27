package com.adloveyou.ms.goosegame.repository;

import com.adloveyou.ms.goosegame.domain.GooseToken;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GooseToken entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GooseTokenRepository extends JpaRepository<GooseToken, Long> {

}
