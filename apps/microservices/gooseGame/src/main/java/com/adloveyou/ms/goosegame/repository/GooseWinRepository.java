package com.adloveyou.ms.goosegame.repository;

import com.adloveyou.ms.goosegame.domain.GooseWin;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GooseWin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GooseWinRepository extends JpaRepository<GooseWin, Long> {

}
