package com.adloveyou.ms.repository;

import com.adloveyou.ms.domain.ad.FileAttachement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FileAttachement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileAttachementRepository extends JpaRepository<FileAttachement, Long> {

}
