package com.adloveyou.ms.game.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.game.service.AdScoreService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.game.service.dto.AdScoreDTO;
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
 * REST controller for managing AdScore.
 */
@RestController
@RequestMapping("/api")
public class AdScoreResource {

    private final Logger log = LoggerFactory.getLogger(AdScoreResource.class);

    private static final String ENTITY_NAME = "adScore";

    private final AdScoreService adScoreService;

    public AdScoreResource(AdScoreService adScoreService) {
        this.adScoreService = adScoreService;
    }

    /**
     * POST  /ad-scores : Create a new adScore.
     *
     * @param adScoreDTO the adScoreDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adScoreDTO, or with status 400 (Bad Request) if the adScore has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ad-scores")
    @Timed
    public ResponseEntity<AdScoreDTO> createAdScore(@RequestBody AdScoreDTO adScoreDTO) throws URISyntaxException {
        log.debug("REST request to save AdScore : {}", adScoreDTO);
        if (adScoreDTO.getId() != null) {
            throw new BadRequestAlertException("A new adScore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdScoreDTO result = adScoreService.save(adScoreDTO);
        return ResponseEntity.created(new URI("/api/ad-scores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ad-scores : Updates an existing adScore.
     *
     * @param adScoreDTO the adScoreDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adScoreDTO,
     * or with status 400 (Bad Request) if the adScoreDTO is not valid,
     * or with status 500 (Internal Server Error) if the adScoreDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ad-scores")
    @Timed
    public ResponseEntity<AdScoreDTO> updateAdScore(@RequestBody AdScoreDTO adScoreDTO) throws URISyntaxException {
        log.debug("REST request to update AdScore : {}", adScoreDTO);
        if (adScoreDTO.getId() == null) {
            return createAdScore(adScoreDTO);
        }
        AdScoreDTO result = adScoreService.save(adScoreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adScoreDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ad-scores : get all the adScores.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of adScores in body
     */
    @GetMapping("/ad-scores")
    @Timed
    public ResponseEntity<List<AdScoreDTO>> getAllAdScores(Pageable pageable) {
        log.debug("REST request to get a page of AdScores");
        Page<AdScoreDTO> page = adScoreService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ad-scores");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ad-scores/:id : get the "id" adScore.
     *
     * @param id the id of the adScoreDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adScoreDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ad-scores/{id}")
    @Timed
    public ResponseEntity<AdScoreDTO> getAdScore(@PathVariable Long id) {
        log.debug("REST request to get AdScore : {}", id);
        AdScoreDTO adScoreDTO = adScoreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(adScoreDTO));
    }

    /**
     * DELETE  /ad-scores/:id : delete the "id" adScore.
     *
     * @param id the id of the adScoreDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ad-scores/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdScore(@PathVariable Long id) {
        log.debug("REST request to delete AdScore : {}", id);
        adScoreService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ad-scores?query=:query : search for the adScore corresponding
     * to the query.
     *
     * @param query the query of the adScore search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ad-scores")
    @Timed
    public ResponseEntity<List<AdScoreDTO>> searchAdScores(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AdScores for query {}", query);
        Page<AdScoreDTO> page = adScoreService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ad-scores");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
