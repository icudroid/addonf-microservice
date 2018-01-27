package com.adloveyou.ms.ad.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.ad.service.AdConfigurationService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.ad.service.dto.AdConfigurationDTO;
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
 * REST controller for managing AdConfiguration.
 */
@RestController
@RequestMapping("/api")
public class AdConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(AdConfigurationResource.class);

    private static final String ENTITY_NAME = "adConfiguration";

    private final AdConfigurationService adConfigurationService;

    public AdConfigurationResource(AdConfigurationService adConfigurationService) {
        this.adConfigurationService = adConfigurationService;
    }

    /**
     * POST  /ad-configurations : Create a new adConfiguration.
     *
     * @param adConfigurationDTO the adConfigurationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adConfigurationDTO, or with status 400 (Bad Request) if the adConfiguration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ad-configurations")
    @Timed
    public ResponseEntity<AdConfigurationDTO> createAdConfiguration(@RequestBody AdConfigurationDTO adConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to save AdConfiguration : {}", adConfigurationDTO);
        if (adConfigurationDTO.getId() != null) {
            throw new BadRequestAlertException("A new adConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdConfigurationDTO result = adConfigurationService.save(adConfigurationDTO);
        return ResponseEntity.created(new URI("/api/ad-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ad-configurations : Updates an existing adConfiguration.
     *
     * @param adConfigurationDTO the adConfigurationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adConfigurationDTO,
     * or with status 400 (Bad Request) if the adConfigurationDTO is not valid,
     * or with status 500 (Internal Server Error) if the adConfigurationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ad-configurations")
    @Timed
    public ResponseEntity<AdConfigurationDTO> updateAdConfiguration(@RequestBody AdConfigurationDTO adConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to update AdConfiguration : {}", adConfigurationDTO);
        if (adConfigurationDTO.getId() == null) {
            return createAdConfiguration(adConfigurationDTO);
        }
        AdConfigurationDTO result = adConfigurationService.save(adConfigurationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adConfigurationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ad-configurations : get all the adConfigurations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of adConfigurations in body
     */
    @GetMapping("/ad-configurations")
    @Timed
    public ResponseEntity<List<AdConfigurationDTO>> getAllAdConfigurations(Pageable pageable) {
        log.debug("REST request to get a page of AdConfigurations");
        Page<AdConfigurationDTO> page = adConfigurationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ad-configurations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ad-configurations/:id : get the "id" adConfiguration.
     *
     * @param id the id of the adConfigurationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adConfigurationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ad-configurations/{id}")
    @Timed
    public ResponseEntity<AdConfigurationDTO> getAdConfiguration(@PathVariable Long id) {
        log.debug("REST request to get AdConfiguration : {}", id);
        AdConfigurationDTO adConfigurationDTO = adConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(adConfigurationDTO));
    }

    /**
     * DELETE  /ad-configurations/:id : delete the "id" adConfiguration.
     *
     * @param id the id of the adConfigurationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ad-configurations/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete AdConfiguration : {}", id);
        adConfigurationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ad-configurations?query=:query : search for the adConfiguration corresponding
     * to the query.
     *
     * @param query the query of the adConfiguration search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ad-configurations")
    @Timed
    public ResponseEntity<List<AdConfigurationDTO>> searchAdConfigurations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AdConfigurations for query {}", query);
        Page<AdConfigurationDTO> page = adConfigurationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ad-configurations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
