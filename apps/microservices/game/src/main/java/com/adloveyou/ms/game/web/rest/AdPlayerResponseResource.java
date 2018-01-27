package com.adloveyou.ms.game.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.game.service.AdPlayerResponseService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.game.service.dto.AdPlayerResponseDTO;
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
 * REST controller for managing AdPlayerResponse.
 */
@RestController
@RequestMapping("/api")
public class AdPlayerResponseResource {

    private final Logger log = LoggerFactory.getLogger(AdPlayerResponseResource.class);

    private static final String ENTITY_NAME = "adPlayerResponse";

    private final AdPlayerResponseService adPlayerResponseService;

    public AdPlayerResponseResource(AdPlayerResponseService adPlayerResponseService) {
        this.adPlayerResponseService = adPlayerResponseService;
    }

    /**
     * POST  /ad-player-responses : Create a new adPlayerResponse.
     *
     * @param adPlayerResponseDTO the adPlayerResponseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adPlayerResponseDTO, or with status 400 (Bad Request) if the adPlayerResponse has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ad-player-responses")
    @Timed
    public ResponseEntity<AdPlayerResponseDTO> createAdPlayerResponse(@RequestBody AdPlayerResponseDTO adPlayerResponseDTO) throws URISyntaxException {
        log.debug("REST request to save AdPlayerResponse : {}", adPlayerResponseDTO);
        if (adPlayerResponseDTO.getId() != null) {
            throw new BadRequestAlertException("A new adPlayerResponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdPlayerResponseDTO result = adPlayerResponseService.save(adPlayerResponseDTO);
        return ResponseEntity.created(new URI("/api/ad-player-responses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ad-player-responses : Updates an existing adPlayerResponse.
     *
     * @param adPlayerResponseDTO the adPlayerResponseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adPlayerResponseDTO,
     * or with status 400 (Bad Request) if the adPlayerResponseDTO is not valid,
     * or with status 500 (Internal Server Error) if the adPlayerResponseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ad-player-responses")
    @Timed
    public ResponseEntity<AdPlayerResponseDTO> updateAdPlayerResponse(@RequestBody AdPlayerResponseDTO adPlayerResponseDTO) throws URISyntaxException {
        log.debug("REST request to update AdPlayerResponse : {}", adPlayerResponseDTO);
        if (adPlayerResponseDTO.getId() == null) {
            return createAdPlayerResponse(adPlayerResponseDTO);
        }
        AdPlayerResponseDTO result = adPlayerResponseService.save(adPlayerResponseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adPlayerResponseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ad-player-responses : get all the adPlayerResponses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of adPlayerResponses in body
     */
    @GetMapping("/ad-player-responses")
    @Timed
    public ResponseEntity<List<AdPlayerResponseDTO>> getAllAdPlayerResponses(Pageable pageable) {
        log.debug("REST request to get a page of AdPlayerResponses");
        Page<AdPlayerResponseDTO> page = adPlayerResponseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ad-player-responses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ad-player-responses/:id : get the "id" adPlayerResponse.
     *
     * @param id the id of the adPlayerResponseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adPlayerResponseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ad-player-responses/{id}")
    @Timed
    public ResponseEntity<AdPlayerResponseDTO> getAdPlayerResponse(@PathVariable Long id) {
        log.debug("REST request to get AdPlayerResponse : {}", id);
        AdPlayerResponseDTO adPlayerResponseDTO = adPlayerResponseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(adPlayerResponseDTO));
    }

    /**
     * DELETE  /ad-player-responses/:id : delete the "id" adPlayerResponse.
     *
     * @param id the id of the adPlayerResponseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ad-player-responses/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdPlayerResponse(@PathVariable Long id) {
        log.debug("REST request to delete AdPlayerResponse : {}", id);
        adPlayerResponseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ad-player-responses?query=:query : search for the adPlayerResponse corresponding
     * to the query.
     *
     * @param query the query of the adPlayerResponse search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ad-player-responses")
    @Timed
    public ResponseEntity<List<AdPlayerResponseDTO>> searchAdPlayerResponses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AdPlayerResponses for query {}", query);
        Page<AdPlayerResponseDTO> page = adPlayerResponseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ad-player-responses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
