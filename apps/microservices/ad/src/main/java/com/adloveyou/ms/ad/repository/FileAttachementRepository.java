package com.adloveyou.ms.ad.repository;

import com.adloveyou.ms.ad.domain.FileAttachement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FileAttachement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileAttachementRepository extends JpaRepository<FileAttachement, Long> {

}
