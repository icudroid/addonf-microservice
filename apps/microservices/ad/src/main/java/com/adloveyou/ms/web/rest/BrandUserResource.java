package com.adloveyou.ms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.service.BrandUserService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.service.dto.BrandUserDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing BrandUser.
 */
@RestController
@RequestMapping("/api")
public class BrandUserResource {

    private final Logger log = LoggerFactory.getLogger(BrandUserResource.class);

    private static final String ENTITY_NAME = "brandUser";

    private final BrandUserService brandUserService;

    public BrandUserResource(BrandUserService brandUserService) {
        this.brandUserService = brandUserService;
    }

    /**
     * POST  /brand-users : Create a new brandUser.
     *
     * @param brandUserDTO the brandUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new brandUserDTO, or with status 400 (Bad Request) if the brandUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/brand-users")
    @Timed
    public ResponseEntity<BrandUserDTO> createBrandUser(@RequestBody BrandUserDTO brandUserDTO) throws URISyntaxException {
        log.debug("REST request to save BrandUser : {}", brandUserDTO);
        if (brandUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new brandUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BrandUserDTO result = brandUserService.save(brandUserDTO);
        return ResponseEntity.created(new URI("/api/brand-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /brand-users : Updates an existing brandUser.
     *
     * @param brandUserDTO the brandUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated brandUserDTO,
     * or with status 400 (Bad Request) if the brandUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the brandUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/brand-users")
    @Timed
    public ResponseEntity<BrandUserDTO> updateBrandUser(@RequestBody BrandUserDTO brandUserDTO) throws URISyntaxException {
        log.debug("REST request to update BrandUser : {}", brandUserDTO);
        if (brandUserDTO.getId() == null) {
            return createBrandUser(brandUserDTO);
        }
        BrandUserDTO result = brandUserService.save(brandUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, brandUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /brand-users : get all the brandUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of brandUsers in body
     */
    @GetMapping("/brand-users")
    @Timed
    public ResponseEntity<List<BrandUserDTO>> getAllBrandUsers(Pageable pageable) {
        log.debug("REST request to get a page of BrandUsers");
        Page<BrandUserDTO> page = brandUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/brand-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /brand-users/:id : get the "id" brandUser.
     *
     * @param id the id of the brandUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the brandUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/brand-users/{id}")
    @Timed
    public ResponseEntity<BrandUserDTO> getBrandUser(@PathVariable Long id) {
        log.debug("REST request to get BrandUser : {}", id);
        BrandUserDTO brandUserDTO = brandUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(brandUserDTO));
    }

    /**
     * DELETE  /brand-users/:id : delete the "id" brandUser.
     *
     * @param id the id of the brandUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/brand-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteBrandUser(@PathVariable Long id) {
        log.debug("REST request to delete BrandUser : {}", id);
        brandUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/brand-users?query=:query : search for the brandUser corresponding
     * to the query.
     *
     * @param query the query of the brandUser search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/brand-users")
    @Timed
    public ResponseEntity<List<BrandUserDTO>> searchBrandUsers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BrandUsers for query {}", query);
        Page<BrandUserDTO> page = brandUserService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/brand-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
