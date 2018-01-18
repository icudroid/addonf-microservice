package com.adloveyou.ms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.service.AdService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.service.dto.AdDTO;
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
 * REST controller for managing Ad.
 */
@RestController
@RequestMapping("/api")
public class AdResource {

    private final Logger log = LoggerFactory.getLogger(AdResource.class);

    private static final String ENTITY_NAME = "ad";

    private final AdService adService;

    public AdResource(AdService adService) {
        this.adService = adService;
    }

    /**
     * POST  /ads : Create a new ad.
     *
     * @param adDTO the adDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adDTO, or with status 400 (Bad Request) if the ad has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ads")
    @Timed
    public ResponseEntity<AdDTO> createAd(@RequestBody AdDTO adDTO) throws URISyntaxException {
        log.debug("REST request to save Ad : {}", adDTO);
        if (adDTO.getId() != null) {
            throw new BadRequestAlertException("A new ad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdDTO result = adService.save(adDTO);
        return ResponseEntity.created(new URI("/api/ads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ads : Updates an existing ad.
     *
     * @param adDTO the adDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adDTO,
     * or with status 400 (Bad Request) if the adDTO is not valid,
     * or with status 500 (Internal Server Error) if the adDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ads")
    @Timed
    public ResponseEntity<AdDTO> updateAd(@RequestBody AdDTO adDTO) throws URISyntaxException {
        log.debug("REST request to update Ad : {}", adDTO);
        if (adDTO.getId() == null) {
            return createAd(adDTO);
        }
        AdDTO result = adService.save(adDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ads : get all the ads.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ads in body
     */
    @GetMapping("/ads")
    @Timed
    public ResponseEntity<List<AdDTO>> getAllAds(Pageable pageable) {
        log.debug("REST request to get a page of Ads");
        Page<AdDTO> page = adService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ads");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ads/:id : get the "id" ad.
     *
     * @param id the id of the adDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ads/{id}")
    @Timed
    public ResponseEntity<AdDTO> getAd(@PathVariable Long id) {
        log.debug("REST request to get Ad : {}", id);
        AdDTO adDTO = adService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(adDTO));
    }

    /**
     * DELETE  /ads/:id : delete the "id" ad.
     *
     * @param id the id of the adDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ads/{id}")
    @Timed
    public ResponseEntity<Void> deleteAd(@PathVariable Long id) {
        log.debug("REST request to delete Ad : {}", id);
        adService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ads?query=:query : search for the ad corresponding
     * to the query.
     *
     * @param query the query of the ad search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ads")
    @Timed
    public ResponseEntity<List<AdDTO>> searchAds(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Ads for query {}", query);
        Page<AdDTO> page = adService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ads");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
