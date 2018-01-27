package com.adloveyou.ms.ad.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.ad.service.AdRestrictionService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.ad.service.dto.AdRestrictionDTO;
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
 * REST controller for managing AdRestriction.
 */
@RestController
@RequestMapping("/api")
public class AdRestrictionResource {

    private final Logger log = LoggerFactory.getLogger(AdRestrictionResource.class);

    private static final String ENTITY_NAME = "adRestriction";

    private final AdRestrictionService adRestrictionService;

    public AdRestrictionResource(AdRestrictionService adRestrictionService) {
        this.adRestrictionService = adRestrictionService;
    }

    /**
     * POST  /ad-restrictions : Create a new adRestriction.
     *
     * @param adRestrictionDTO the adRestrictionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adRestrictionDTO, or with status 400 (Bad Request) if the adRestriction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ad-restrictions")
    @Timed
    public ResponseEntity<AdRestrictionDTO> createAdRestriction(@RequestBody AdRestrictionDTO adRestrictionDTO) throws URISyntaxException {
        log.debug("REST request to save AdRestriction : {}", adRestrictionDTO);
        if (adRestrictionDTO.getId() != null) {
            throw new BadRequestAlertException("A new adRestriction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdRestrictionDTO result = adRestrictionService.save(adRestrictionDTO);
        return ResponseEntity.created(new URI("/api/ad-restrictions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ad-restrictions : Updates an existing adRestriction.
     *
     * @param adRestrictionDTO the adRestrictionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adRestrictionDTO,
     * or with status 400 (Bad Request) if the adRestrictionDTO is not valid,
     * or with status 500 (Internal Server Error) if the adRestrictionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ad-restrictions")
    @Timed
    public ResponseEntity<AdRestrictionDTO> updateAdRestriction(@RequestBody AdRestrictionDTO adRestrictionDTO) throws URISyntaxException {
        log.debug("REST request to update AdRestriction : {}", adRestrictionDTO);
        if (adRestrictionDTO.getId() == null) {
            return createAdRestriction(adRestrictionDTO);
        }
        AdRestrictionDTO result = adRestrictionService.save(adRestrictionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adRestrictionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ad-restrictions : get all the adRestrictions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of adRestrictions in body
     */
    @GetMapping("/ad-restrictions")
    @Timed
    public ResponseEntity<List<AdRestrictionDTO>> getAllAdRestrictions(Pageable pageable) {
        log.debug("REST request to get a page of AdRestrictions");
        Page<AdRestrictionDTO> page = adRestrictionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ad-restrictions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ad-restrictions/:id : get the "id" adRestriction.
     *
     * @param id the id of the adRestrictionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adRestrictionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ad-restrictions/{id}")
    @Timed
    public ResponseEntity<AdRestrictionDTO> getAdRestriction(@PathVariable Long id) {
        log.debug("REST request to get AdRestriction : {}", id);
        AdRestrictionDTO adRestrictionDTO = adRestrictionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(adRestrictionDTO));
    }

    /**
     * DELETE  /ad-restrictions/:id : delete the "id" adRestriction.
     *
     * @param id the id of the adRestrictionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ad-restrictions/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdRestriction(@PathVariable Long id) {
        log.debug("REST request to delete AdRestriction : {}", id);
        adRestrictionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ad-restrictions?query=:query : search for the adRestriction corresponding
     * to the query.
     *
     * @param query the query of the adRestriction search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ad-restrictions")
    @Timed
    public ResponseEntity<List<AdRestrictionDTO>> searchAdRestrictions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AdRestrictions for query {}", query);
        Page<AdRestrictionDTO> page = adRestrictionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ad-restrictions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
