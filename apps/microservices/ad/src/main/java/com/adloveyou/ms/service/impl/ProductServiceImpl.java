package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.ProductService;
import com.adloveyou.ms.domain.Product;
import com.adloveyou.ms.service.dto.ProductDTO;
import com.adloveyou.ms.service.mapper.EntityMapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Product.
 */
@Service
@Transactional
public class ProductServiceImpl extends GenericServiceImpl<Product, ProductDTO, Long> implements ProductService{

    public ProductServiceImpl(EntityMapper<ProductDTO, Product> mapper, ElasticsearchRepository<Product, Long> elasticsearchRepository, JpaRepository<Product, Long> repository) {
        super(mapper, elasticsearchRepository, repository);
    }
}
