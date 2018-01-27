package com.adloveyou.ms.game.repository;

import com.adloveyou.ms.game.domain.AdPlayerResponse;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the AdPlayerResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdPlayerResponseRepository extends JpaRepository<AdPlayerResponse, Long> {
    @Query("select distinct ad_player_response from AdPlayerResponse ad_player_response left join fetch ad_player_response.responses")
    List<AdPlayerResponse> findAllWithEagerRelationships();

    @Query("select ad_player_response from AdPlayerResponse ad_player_response left join fetch ad_player_response.responses where ad_player_response.id =:id")
    AdPlayerResponse findOneWithEagerRelationships(@Param("id") Long id);

}
