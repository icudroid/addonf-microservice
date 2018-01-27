package com.adloveyou.ms.goosegame.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.goosegame.service.GooseCaseService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.goosegame.service.dto.GooseCaseDTO;
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
 * REST controller for managing GooseCase.
 */
@RestController
@RequestMapping("/api")
public class GooseCaseResource {

    private final Logger log = LoggerFactory.getLogger(GooseCaseResource.class);

    private static final String ENTITY_NAME = "gooseCase";

    private final GooseCaseService gooseCaseService;

    public GooseCaseResource(GooseCaseService gooseCaseService) {
        this.gooseCaseService = gooseCaseService;
    }

    /**
     * POST  /goose-cases : Create a new gooseCase.
     *
     * @param gooseCaseDTO the gooseCaseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gooseCaseDTO, or with status 400 (Bad Request) if the gooseCase has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/goose-cases")
    @Timed
    public ResponseEntity<GooseCaseDTO> createGooseCase(@RequestBody GooseCaseDTO gooseCaseDTO) throws URISyntaxException {
        log.debug("REST request to save GooseCase : {}", gooseCaseDTO);
        if (gooseCaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new gooseCase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GooseCaseDTO result = gooseCaseService.save(gooseCaseDTO);
        return ResponseEntity.created(new URI("/api/goose-cases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /goose-cases : Updates an existing gooseCase.
     *
     * @param gooseCaseDTO the gooseCaseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gooseCaseDTO,
     * or with status 400 (Bad Request) if the gooseCaseDTO is not valid,
     * or with status 500 (Internal Server Error) if the gooseCaseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/goose-cases")
    @Timed
    public ResponseEntity<GooseCaseDTO> updateGooseCase(@RequestBody GooseCaseDTO gooseCaseDTO) throws URISyntaxException {
        log.debug("REST request to update GooseCase : {}", gooseCaseDTO);
        if (gooseCaseDTO.getId() == null) {
            return createGooseCase(gooseCaseDTO);
        }
        GooseCaseDTO result = gooseCaseService.save(gooseCaseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gooseCaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /goose-cases : get all the gooseCases.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of gooseCases in body
     */
    @GetMapping("/goose-cases")
    @Timed
    public ResponseEntity<List<GooseCaseDTO>> getAllGooseCases(Pageable pageable) {
        log.debug("REST request to get a page of GooseCases");
        Page<GooseCaseDTO> page = gooseCaseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/goose-cases");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /goose-cases/:id : get the "id" gooseCase.
     *
     * @param id the id of the gooseCaseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gooseCaseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/goose-cases/{id}")
    @Timed
    public ResponseEntity<GooseCaseDTO> getGooseCase(@PathVariable Long id) {
        log.debug("REST request to get GooseCase : {}", id);
        GooseCaseDTO gooseCaseDTO = gooseCaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gooseCaseDTO));
    }

    /**
     * DELETE  /goose-cases/:id : delete the "id" gooseCase.
     *
     * @param id the id of the gooseCaseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/goose-cases/{id}")
    @Timed
    public ResponseEntity<Void> deleteGooseCase(@PathVariable Long id) {
        log.debug("REST request to delete GooseCase : {}", id);
        gooseCaseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/goose-cases?query=:query : search for the gooseCase corresponding
     * to the query.
     *
     * @param query the query of the gooseCase search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/goose-cases")
    @Timed
    public ResponseEntity<List<GooseCaseDTO>> searchGooseCases(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GooseCases for query {}", query);
        Page<GooseCaseDTO> page = gooseCaseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/goose-cases");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
