package com.adloveyou.ms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.service.AdCampaingService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.service.dto.AdCampaingDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AdCampaing.
 */
@RestController
@RequestMapping("/api")
public class AdCampaingResource {

    private final Logger log = LoggerFactory.getLogger(AdCampaingResource.class);

    private static final String ENTITY_NAME = "adCampaing";

    private final AdCampaingService adCampaingService;

    public AdCampaingResource(AdCampaingService adCampaingService) {
        this.adCampaingService = adCampaingService;
    }

    /**
     * POST  /ad-campaings : Create a new adCampaing.
     *
     * @param adCampaingDTO the adCampaingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adCampaingDTO, or with status 400 (Bad Request) if the adCampaing has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ad-campaings")
    @Timed
    public ResponseEntity<AdCampaingDTO> createAdCampaing(@Valid @RequestBody AdCampaingDTO adCampaingDTO) throws URISyntaxException {
        log.debug("REST request to save AdCampaing : {}", adCampaingDTO);
        if (adCampaingDTO.getId() != null) {
            throw new BadRequestAlertException("A new adCampaing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdCampaingDTO result = adCampaingService.save(adCampaingDTO);
        return ResponseEntity.created(new URI("/api/ad-campaings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ad-campaings : Updates an existing adCampaing.
     *
     * @param adCampaingDTO the adCampaingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adCampaingDTO,
     * or with status 400 (Bad Request) if the adCampaingDTO is not valid,
     * or with status 500 (Internal Server Error) if the adCampaingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ad-campaings")
    @Timed
    public ResponseEntity<AdCampaingDTO> updateAdCampaing(@Valid @RequestBody AdCampaingDTO adCampaingDTO) throws URISyntaxException {
        log.debug("REST request to update AdCampaing : {}", adCampaingDTO);
        if (adCampaingDTO.getId() == null) {
            return createAdCampaing(adCampaingDTO);
        }
        AdCampaingDTO result = adCampaingService.save(adCampaingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adCampaingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ad-campaings : get all the adCampaings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of adCampaings in body
     */
    @GetMapping("/ad-campaings")
    @Timed
    public ResponseEntity<List<AdCampaingDTO>> getAllAdCampaings(Pageable pageable) {
        log.debug("REST request to get a page of AdCampaings");
        Page<AdCampaingDTO> page = adCampaingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ad-campaings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ad-campaings/:id : get the "id" adCampaing.
     *
     * @param id the id of the adCampaingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adCampaingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ad-campaings/{id}")
    @Timed
    public ResponseEntity<AdCampaingDTO> getAdCampaing(@PathVariable Long id) {
        log.debug("REST request to get AdCampaing : {}", id);
        AdCampaingDTO adCampaingDTO = adCampaingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(adCampaingDTO));
    }

    /**
     * DELETE  /ad-campaings/:id : delete the "id" adCampaing.
     *
     * @param id the id of the adCampaingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ad-campaings/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdCampaing(@PathVariable Long id) {
        log.debug("REST request to delete AdCampaing : {}", id);
        adCampaingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ad-campaings?query=:query : search for the adCampaing corresponding
     * to the query.
     *
     * @param query the query of the adCampaing search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ad-campaings")
    @Timed
    public ResponseEntity<List<AdCampaingDTO>> searchAdCampaings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AdCampaings for query {}", query);
        Page<AdCampaingDTO> page = adCampaingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ad-campaings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
