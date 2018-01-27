package com.adloveyou.ms.ad.repository;

import com.adloveyou.ms.ad.domain.MeanOfContact;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MeanOfContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeanOfContactRepository extends JpaRepository<MeanOfContact, Long> {

}
