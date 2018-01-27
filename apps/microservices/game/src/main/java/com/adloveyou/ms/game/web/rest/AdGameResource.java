package com.adloveyou.ms.game.web.rest;

import com.adloveyou.ms.game.service.AdGameService;
import com.adloveyou.ms.game.service.dto.AdGameDTO;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
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

/**
 * REST controller for managing AdGame.
 */
@RestController
@RequestMapping("/api")
public class AdGameResource {

    private final Logger log = LoggerFactory.getLogger(AdGameResource.class);

    private static final String ENTITY_NAME = "adGame";

    private final AdGameService adGameService;

    public AdGameResource(AdGameService adGameService) {
        this.adGameService = adGameService;
    }

    /**
     * POST  /ad-games : Create a new adGame.
     *
     * @param adGameDTO the adGameDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adGameDTO, or with status 400 (Bad Request) if the adGame has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ad-games")
    @Timed
    public ResponseEntity<AdGameDTO> createAdGame(@RequestBody AdGameDTO adGameDTO) throws URISyntaxException {
        log.debug("REST request to save AdGame : {}", adGameDTO);
        if (adGameDTO.getId() != null) {
            throw new BadRequestAlertException("A new adGame cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdGameDTO result = adGameService.save(adGameDTO);
        return ResponseEntity.created(new URI("/api/ad-games/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ad-games : Updates an existing adGame.
     *
     * @param adGameDTO the adGameDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adGameDTO,
     * or with status 400 (Bad Request) if the adGameDTO is not valid,
     * or with status 500 (Internal Server Error) if the adGameDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ad-games")
    @Timed
    public ResponseEntity<AdGameDTO> updateAdGame(@RequestBody AdGameDTO adGameDTO) throws URISyntaxException {
        log.debug("REST request to update AdGame : {}", adGameDTO);
        if (adGameDTO.getId() == null) {
            return createAdGame(adGameDTO);
        }
        AdGameDTO result = adGameService.save(adGameDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adGameDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ad-games : get all the adGames.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of adGames in body
     */
    @GetMapping("/ad-games")
    @Timed
    public ResponseEntity<List<AdGameDTO>> getAllAdGames(Pageable pageable) {
        log.debug("REST request to get a page of AdGames");
        Page<AdGameDTO> page = adGameService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ad-games");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ad-games/:id : get the "id" adGame.
     *
     * @param id the id of the adGameDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adGameDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ad-games/{id}")
    @Timed
    public ResponseEntity<AdGameDTO> getAdGame(@PathVariable Long id) {
        log.debug("REST request to get AdGame : {}", id);
        AdGameDTO adGameDTO = adGameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(adGameDTO));
    }

    /**
     * DELETE  /ad-games/:id : delete the "id" adGame.
     *
     * @param id the id of the adGameDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ad-games/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdGame(@PathVariable Long id) {
        log.debug("REST request to delete AdGame : {}", id);
        adGameService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ad-games?query=:query : search for the adGame corresponding
     * to the query.
     *
     * @param query the query of the adGame search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ad-games")
    @Timed
    public ResponseEntity<List<AdGameDTO>> searchAdGames(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AdGames for query {}", query);
        Page<AdGameDTO> page = adGameService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ad-games");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
