package com.adloveyou.ms.goosegame.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.goosegame.service.GooseLevelService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.goosegame.service.dto.GooseLevelDTO;
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
 * REST controller for managing GooseLevel.
 */
@RestController
@RequestMapping("/api")
public class GooseLevelResource {

    private final Logger log = LoggerFactory.getLogger(GooseLevelResource.class);

    private static final String ENTITY_NAME = "gooseLevel";

    private final GooseLevelService gooseLevelService;

    public GooseLevelResource(GooseLevelService gooseLevelService) {
        this.gooseLevelService = gooseLevelService;
    }

    /**
     * POST  /goose-levels : Create a new gooseLevel.
     *
     * @param gooseLevelDTO the gooseLevelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gooseLevelDTO, or with status 400 (Bad Request) if the gooseLevel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/goose-levels")
    @Timed
    public ResponseEntity<GooseLevelDTO> createGooseLevel(@RequestBody GooseLevelDTO gooseLevelDTO) throws URISyntaxException {
        log.debug("REST request to save GooseLevel : {}", gooseLevelDTO);
        if (gooseLevelDTO.getId() != null) {
            throw new BadRequestAlertException("A new gooseLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GooseLevelDTO result = gooseLevelService.save(gooseLevelDTO);
        return ResponseEntity.created(new URI("/api/goose-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /goose-levels : Updates an existing gooseLevel.
     *
     * @param gooseLevelDTO the gooseLevelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gooseLevelDTO,
     * or with status 400 (Bad Request) if the gooseLevelDTO is not valid,
     * or with status 500 (Internal Server Error) if the gooseLevelDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/goose-levels")
    @Timed
    public ResponseEntity<GooseLevelDTO> updateGooseLevel(@RequestBody GooseLevelDTO gooseLevelDTO) throws URISyntaxException {
        log.debug("REST request to update GooseLevel : {}", gooseLevelDTO);
        if (gooseLevelDTO.getId() == null) {
            return createGooseLevel(gooseLevelDTO);
        }
        GooseLevelDTO result = gooseLevelService.save(gooseLevelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gooseLevelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /goose-levels : get all the gooseLevels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of gooseLevels in body
     */
    @GetMapping("/goose-levels")
    @Timed
    public ResponseEntity<List<GooseLevelDTO>> getAllGooseLevels(Pageable pageable) {
        log.debug("REST request to get a page of GooseLevels");
        Page<GooseLevelDTO> page = gooseLevelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/goose-levels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /goose-levels/:id : get the "id" gooseLevel.
     *
     * @param id the id of the gooseLevelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gooseLevelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/goose-levels/{id}")
    @Timed
    public ResponseEntity<GooseLevelDTO> getGooseLevel(@PathVariable Long id) {
        log.debug("REST request to get GooseLevel : {}", id);
        GooseLevelDTO gooseLevelDTO = gooseLevelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gooseLevelDTO));
    }

    /**
     * DELETE  /goose-levels/:id : delete the "id" gooseLevel.
     *
     * @param id the id of the gooseLevelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/goose-levels/{id}")
    @Timed
    public ResponseEntity<Void> deleteGooseLevel(@PathVariable Long id) {
        log.debug("REST request to delete GooseLevel : {}", id);
        gooseLevelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/goose-levels?query=:query : search for the gooseLevel corresponding
     * to the query.
     *
     * @param query the query of the gooseLevel search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/goose-levels")
    @Timed
    public ResponseEntity<List<GooseLevelDTO>> searchGooseLevels(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GooseLevels for query {}", query);
        Page<GooseLevelDTO> page = gooseLevelService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/goose-levels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
