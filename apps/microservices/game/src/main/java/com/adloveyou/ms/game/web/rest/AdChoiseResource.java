package com.adloveyou.ms.game.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.game.service.AdChoiseService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.game.service.dto.AdChoiseDTO;
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
 * REST controller for managing AdChoise.
 */
@RestController
@RequestMapping("/api")
public class AdChoiseResource {

    private final Logger log = LoggerFactory.getLogger(AdChoiseResource.class);

    private static final String ENTITY_NAME = "adChoise";

    private final AdChoiseService adChoiseService;

    public AdChoiseResource(AdChoiseService adChoiseService) {
        this.adChoiseService = adChoiseService;
    }

    /**
     * POST  /ad-choises : Create a new adChoise.
     *
     * @param adChoiseDTO the adChoiseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adChoiseDTO, or with status 400 (Bad Request) if the adChoise has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ad-choises")
    @Timed
    public ResponseEntity<AdChoiseDTO> createAdChoise(@RequestBody AdChoiseDTO adChoiseDTO) throws URISyntaxException {
        log.debug("REST request to save AdChoise : {}", adChoiseDTO);
        if (adChoiseDTO.getId() != null) {
            throw new BadRequestAlertException("A new adChoise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdChoiseDTO result = adChoiseService.save(adChoiseDTO);
        return ResponseEntity.created(new URI("/api/ad-choises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ad-choises : Updates an existing adChoise.
     *
     * @param adChoiseDTO the adChoiseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adChoiseDTO,
     * or with status 400 (Bad Request) if the adChoiseDTO is not valid,
     * or with status 500 (Internal Server Error) if the adChoiseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ad-choises")
    @Timed
    public ResponseEntity<AdChoiseDTO> updateAdChoise(@RequestBody AdChoiseDTO adChoiseDTO) throws URISyntaxException {
        log.debug("REST request to update AdChoise : {}", adChoiseDTO);
        if (adChoiseDTO.getId() == null) {
            return createAdChoise(adChoiseDTO);
        }
        AdChoiseDTO result = adChoiseService.save(adChoiseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adChoiseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ad-choises : get all the adChoises.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of adChoises in body
     */
    @GetMapping("/ad-choises")
    @Timed
    public ResponseEntity<List<AdChoiseDTO>> getAllAdChoises(Pageable pageable) {
        log.debug("REST request to get a page of AdChoises");
        Page<AdChoiseDTO> page = adChoiseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ad-choises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ad-choises/:id : get the "id" adChoise.
     *
     * @param id the id of the adChoiseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adChoiseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ad-choises/{id}")
    @Timed
    public ResponseEntity<AdChoiseDTO> getAdChoise(@PathVariable Long id) {
        log.debug("REST request to get AdChoise : {}", id);
        AdChoiseDTO adChoiseDTO = adChoiseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(adChoiseDTO));
    }

    /**
     * DELETE  /ad-choises/:id : delete the "id" adChoise.
     *
     * @param id the id of the adChoiseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ad-choises/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdChoise(@PathVariable Long id) {
        log.debug("REST request to delete AdChoise : {}", id);
        adChoiseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ad-choises?query=:query : search for the adChoise corresponding
     * to the query.
     *
     * @param query the query of the adChoise search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ad-choises")
    @Timed
    public ResponseEntity<List<AdChoiseDTO>> searchAdChoises(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AdChoises for query {}", query);
        Page<AdChoiseDTO> page = adChoiseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ad-choises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
