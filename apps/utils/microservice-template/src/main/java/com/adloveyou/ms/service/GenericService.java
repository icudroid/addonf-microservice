package com.adloveyou.ms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

public interface GenericService<DOMAIN, PK extends Serializable> {
    DOMAIN save(DOMAIN obj);
    Page<DOMAIN> findAll(Pageable pageable);
    DOMAIN findOne(PK id);
    void delete(PK id);
    Page<DOMAIN> search(String query, Pageable pageable);
}
