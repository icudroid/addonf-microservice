package com.adloveyou.ms.ad.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.ad.service.AgencyUserService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.ad.service.dto.AgencyUserDTO;
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
 * REST controller for managing AgencyUser.
 */
@RestController
@RequestMapping("/api")
public class AgencyUserResource {

    private final Logger log = LoggerFactory.getLogger(AgencyUserResource.class);

    private static final String ENTITY_NAME = "agencyUser";

    private final AgencyUserService agencyUserService;

    public AgencyUserResource(AgencyUserService agencyUserService) {
        this.agencyUserService = agencyUserService;
    }

    /**
     * POST  /agency-users : Create a new agencyUser.
     *
     * @param agencyUserDTO the agencyUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agencyUserDTO, or with status 400 (Bad Request) if the agencyUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agency-users")
    @Timed
    public ResponseEntity<AgencyUserDTO> createAgencyUser(@Valid @RequestBody AgencyUserDTO agencyUserDTO) throws URISyntaxException {
        log.debug("REST request to save AgencyUser : {}", agencyUserDTO);
        if (agencyUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new agencyUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgencyUserDTO result = agencyUserService.save(agencyUserDTO);
        return ResponseEntity.created(new URI("/api/agency-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /agency-users : Updates an existing agencyUser.
     *
     * @param agencyUserDTO the agencyUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agencyUserDTO,
     * or with status 400 (Bad Request) if the agencyUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the agencyUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/agency-users")
    @Timed
    public ResponseEntity<AgencyUserDTO> updateAgencyUser(@Valid @RequestBody AgencyUserDTO agencyUserDTO) throws URISyntaxException {
        log.debug("REST request to update AgencyUser : {}", agencyUserDTO);
        if (agencyUserDTO.getId() == null) {
            return createAgencyUser(agencyUserDTO);
        }
        AgencyUserDTO result = agencyUserService.save(agencyUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, agencyUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /agency-users : get all the agencyUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of agencyUsers in body
     */
    @GetMapping("/agency-users")
    @Timed
    public ResponseEntity<List<AgencyUserDTO>> getAllAgencyUsers(Pageable pageable) {
        log.debug("REST request to get a page of AgencyUsers");
        Page<AgencyUserDTO> page = agencyUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/agency-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /agency-users/:id : get the "id" agencyUser.
     *
     * @param id the id of the agencyUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agencyUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/agency-users/{id}")
    @Timed
    public ResponseEntity<AgencyUserDTO> getAgencyUser(@PathVariable Long id) {
        log.debug("REST request to get AgencyUser : {}", id);
        AgencyUserDTO agencyUserDTO = agencyUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(agencyUserDTO));
    }

    /**
     * DELETE  /agency-users/:id : delete the "id" agencyUser.
     *
     * @param id the id of the agencyUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/agency-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteAgencyUser(@PathVariable Long id) {
        log.debug("REST request to delete AgencyUser : {}", id);
        agencyUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/agency-users?query=:query : search for the agencyUser corresponding
     * to the query.
     *
     * @param query the query of the agencyUser search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/agency-users")
    @Timed
    public ResponseEntity<List<AgencyUserDTO>> searchAgencyUsers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AgencyUsers for query {}", query);
        Page<AgencyUserDTO> page = agencyUserService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/agency-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
