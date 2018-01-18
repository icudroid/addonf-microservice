package com.adloveyou.ms.repository;

import com.adloveyou.ms.domain.MeanOfContact;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MeanOfContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeanOfContactRepository extends JpaRepository<MeanOfContact, Long> {

}
