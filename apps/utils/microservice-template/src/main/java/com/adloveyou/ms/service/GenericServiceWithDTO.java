package com.adloveyou.ms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface GenericServiceWithDTO<CLASS, DTO, PK extends Serializable> {
    DTO save(DTO adCampaingDTO);
    Page<DTO> findAll(Pageable pageable);
    DTO findOne(PK id);
    void delete(PK id);
    Page<DTO> search(String query, Pageable pageable);
}
