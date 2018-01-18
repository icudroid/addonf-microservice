package com.adloveyou.ms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.service.AgencyService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.service.dto.AgencyDTO;
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
 * REST controller for managing Agency.
 */
@RestController
@RequestMapping("/api")
public class AgencyResource {

    private final Logger log = LoggerFactory.getLogger(AgencyResource.class);

    private static final String ENTITY_NAME = "agency";

    private final AgencyService agencyService;

    public AgencyResource(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    /**
     * POST  /agencies : Create a new agency.
     *
     * @param agencyDTO the agencyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agencyDTO, or with status 400 (Bad Request) if the agency has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agencies")
    @Timed
    public ResponseEntity<AgencyDTO> createAgency(@RequestBody AgencyDTO agencyDTO) throws URISyntaxException {
        log.debug("REST request to save Agency : {}", agencyDTO);
        if (agencyDTO.getId() != null) {
            throw new BadRequestAlertException("A new agency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgencyDTO result = agencyService.save(agencyDTO);
        return ResponseEntity.created(new URI("/api/agencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /agencies : Updates an existing agency.
     *
     * @param agencyDTO the agencyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agencyDTO,
     * or with status 400 (Bad Request) if the agencyDTO is not valid,
     * or with status 500 (Internal Server Error) if the agencyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/agencies")
    @Timed
    public ResponseEntity<AgencyDTO> updateAgency(@RequestBody AgencyDTO agencyDTO) throws URISyntaxException {
        log.debug("REST request to update Agency : {}", agencyDTO);
        if (agencyDTO.getId() == null) {
            return createAgency(agencyDTO);
        }
        AgencyDTO result = agencyService.save(agencyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, agencyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /agencies : get all the agencies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of agencies in body
     */
    @GetMapping("/agencies")
    @Timed
    public ResponseEntity<List<AgencyDTO>> getAllAgencies(Pageable pageable) {
        log.debug("REST request to get a page of Agencies");
        Page<AgencyDTO> page = agencyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/agencies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /agencies/:id : get the "id" agency.
     *
     * @param id the id of the agencyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agencyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/agencies/{id}")
    @Timed
    public ResponseEntity<AgencyDTO> getAgency(@PathVariable Long id) {
        log.debug("REST request to get Agency : {}", id);
        AgencyDTO agencyDTO = agencyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(agencyDTO));
    }

    /**
     * DELETE  /agencies/:id : delete the "id" agency.
     *
     * @param id the id of the agencyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/agencies/{id}")
    @Timed
    public ResponseEntity<Void> deleteAgency(@PathVariable Long id) {
        log.debug("REST request to delete Agency : {}", id);
        agencyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/agencies?query=:query : search for the agency corresponding
     * to the query.
     *
     * @param query the query of the agency search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/agencies")
    @Timed
    public ResponseEntity<List<AgencyDTO>> searchAgencies(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Agencies for query {}", query);
        Page<AgencyDTO> page = agencyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/agencies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
