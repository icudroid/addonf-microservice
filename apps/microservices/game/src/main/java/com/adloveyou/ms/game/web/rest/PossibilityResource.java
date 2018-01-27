package com.adloveyou.ms.game.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.game.service.PossibilityService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.game.service.dto.PossibilityDTO;
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
 * REST controller for managing Possibility.
 */
@RestController
@RequestMapping("/api")
public class PossibilityResource {

    private final Logger log = LoggerFactory.getLogger(PossibilityResource.class);

    private static final String ENTITY_NAME = "possibility";

    private final PossibilityService possibilityService;

    public PossibilityResource(PossibilityService possibilityService) {
        this.possibilityService = possibilityService;
    }

    /**
     * POST  /possibilities : Create a new possibility.
     *
     * @param possibilityDTO the possibilityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new possibilityDTO, or with status 400 (Bad Request) if the possibility has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/possibilities")
    @Timed
    public ResponseEntity<PossibilityDTO> createPossibility(@RequestBody PossibilityDTO possibilityDTO) throws URISyntaxException {
        log.debug("REST request to save Possibility : {}", possibilityDTO);
        if (possibilityDTO.getId() != null) {
            throw new BadRequestAlertException("A new possibility cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PossibilityDTO result = possibilityService.save(possibilityDTO);
        return ResponseEntity.created(new URI("/api/possibilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /possibilities : Updates an existing possibility.
     *
     * @param possibilityDTO the possibilityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated possibilityDTO,
     * or with status 400 (Bad Request) if the possibilityDTO is not valid,
     * or with status 500 (Internal Server Error) if the possibilityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/possibilities")
    @Timed
    public ResponseEntity<PossibilityDTO> updatePossibility(@RequestBody PossibilityDTO possibilityDTO) throws URISyntaxException {
        log.debug("REST request to update Possibility : {}", possibilityDTO);
        if (possibilityDTO.getId() == null) {
            return createPossibility(possibilityDTO);
        }
        PossibilityDTO result = possibilityService.save(possibilityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, possibilityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /possibilities : get all the possibilities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of possibilities in body
     */
    @GetMapping("/possibilities")
    @Timed
    public ResponseEntity<List<PossibilityDTO>> getAllPossibilities(Pageable pageable) {
        log.debug("REST request to get a page of Possibilities");
        Page<PossibilityDTO> page = possibilityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/possibilities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /possibilities/:id : get the "id" possibility.
     *
     * @param id the id of the possibilityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the possibilityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/possibilities/{id}")
    @Timed
    public ResponseEntity<PossibilityDTO> getPossibility(@PathVariable Long id) {
        log.debug("REST request to get Possibility : {}", id);
        PossibilityDTO possibilityDTO = possibilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(possibilityDTO));
    }

    /**
     * DELETE  /possibilities/:id : delete the "id" possibility.
     *
     * @param id the id of the possibilityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/possibilities/{id}")
    @Timed
    public ResponseEntity<Void> deletePossibility(@PathVariable Long id) {
        log.debug("REST request to delete Possibility : {}", id);
        possibilityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/possibilities?query=:query : search for the possibility corresponding
     * to the query.
     *
     * @param query the query of the possibility search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/possibilities")
    @Timed
    public ResponseEntity<List<PossibilityDTO>> searchPossibilities(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Possibilities for query {}", query);
        Page<PossibilityDTO> page = possibilityService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/possibilities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
