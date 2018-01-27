package com.adloveyou.ms.ad.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.ad.service.MeanOfContactService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.ad.service.dto.MeanOfContactDTO;
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
 * REST controller for managing MeanOfContact.
 */
@RestController
@RequestMapping("/api")
public class MeanOfContactResource {

    private final Logger log = LoggerFactory.getLogger(MeanOfContactResource.class);

    private static final String ENTITY_NAME = "meanOfContact";

    private final MeanOfContactService meanOfContactService;

    public MeanOfContactResource(MeanOfContactService meanOfContactService) {
        this.meanOfContactService = meanOfContactService;
    }

    /**
     * POST  /mean-of-contacts : Create a new meanOfContact.
     *
     * @param meanOfContactDTO the meanOfContactDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new meanOfContactDTO, or with status 400 (Bad Request) if the meanOfContact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mean-of-contacts")
    @Timed
    public ResponseEntity<MeanOfContactDTO> createMeanOfContact(@Valid @RequestBody MeanOfContactDTO meanOfContactDTO) throws URISyntaxException {
        log.debug("REST request to save MeanOfContact : {}", meanOfContactDTO);
        if (meanOfContactDTO.getId() != null) {
            throw new BadRequestAlertException("A new meanOfContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeanOfContactDTO result = meanOfContactService.save(meanOfContactDTO);
        return ResponseEntity.created(new URI("/api/mean-of-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mean-of-contacts : Updates an existing meanOfContact.
     *
     * @param meanOfContactDTO the meanOfContactDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated meanOfContactDTO,
     * or with status 400 (Bad Request) if the meanOfContactDTO is not valid,
     * or with status 500 (Internal Server Error) if the meanOfContactDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mean-of-contacts")
    @Timed
    public ResponseEntity<MeanOfContactDTO> updateMeanOfContact(@Valid @RequestBody MeanOfContactDTO meanOfContactDTO) throws URISyntaxException {
        log.debug("REST request to update MeanOfContact : {}", meanOfContactDTO);
        if (meanOfContactDTO.getId() == null) {
            return createMeanOfContact(meanOfContactDTO);
        }
        MeanOfContactDTO result = meanOfContactService.save(meanOfContactDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, meanOfContactDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mean-of-contacts : get all the meanOfContacts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of meanOfContacts in body
     */
    @GetMapping("/mean-of-contacts")
    @Timed
    public ResponseEntity<List<MeanOfContactDTO>> getAllMeanOfContacts(Pageable pageable) {
        log.debug("REST request to get a page of MeanOfContacts");
        Page<MeanOfContactDTO> page = meanOfContactService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mean-of-contacts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mean-of-contacts/:id : get the "id" meanOfContact.
     *
     * @param id the id of the meanOfContactDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the meanOfContactDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mean-of-contacts/{id}")
    @Timed
    public ResponseEntity<MeanOfContactDTO> getMeanOfContact(@PathVariable Long id) {
        log.debug("REST request to get MeanOfContact : {}", id);
        MeanOfContactDTO meanOfContactDTO = meanOfContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(meanOfContactDTO));
    }

    /**
     * DELETE  /mean-of-contacts/:id : delete the "id" meanOfContact.
     *
     * @param id the id of the meanOfContactDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mean-of-contacts/{id}")
    @Timed
    public ResponseEntity<Void> deleteMeanOfContact(@PathVariable Long id) {
        log.debug("REST request to delete MeanOfContact : {}", id);
        meanOfContactService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/mean-of-contacts?query=:query : search for the meanOfContact corresponding
     * to the query.
     *
     * @param query the query of the meanOfContact search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/mean-of-contacts")
    @Timed
    public ResponseEntity<List<MeanOfContactDTO>> searchMeanOfContacts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MeanOfContacts for query {}", query);
        Page<MeanOfContactDTO> page = meanOfContactService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/mean-of-contacts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
