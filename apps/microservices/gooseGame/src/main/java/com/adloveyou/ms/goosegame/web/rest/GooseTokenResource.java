package com.adloveyou.ms.goosegame.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.goosegame.service.GooseTokenService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.goosegame.service.dto.GooseTokenDTO;
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
 * REST controller for managing GooseToken.
 */
@RestController
@RequestMapping("/api")
public class GooseTokenResource {

    private final Logger log = LoggerFactory.getLogger(GooseTokenResource.class);

    private static final String ENTITY_NAME = "gooseToken";

    private final GooseTokenService gooseTokenService;

    public GooseTokenResource(GooseTokenService gooseTokenService) {
        this.gooseTokenService = gooseTokenService;
    }

    /**
     * POST  /goose-tokens : Create a new gooseToken.
     *
     * @param gooseTokenDTO the gooseTokenDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gooseTokenDTO, or with status 400 (Bad Request) if the gooseToken has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/goose-tokens")
    @Timed
    public ResponseEntity<GooseTokenDTO> createGooseToken(@RequestBody GooseTokenDTO gooseTokenDTO) throws URISyntaxException {
        log.debug("REST request to save GooseToken : {}", gooseTokenDTO);
        if (gooseTokenDTO.getId() != null) {
            throw new BadRequestAlertException("A new gooseToken cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GooseTokenDTO result = gooseTokenService.save(gooseTokenDTO);
        return ResponseEntity.created(new URI("/api/goose-tokens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /goose-tokens : Updates an existing gooseToken.
     *
     * @param gooseTokenDTO the gooseTokenDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gooseTokenDTO,
     * or with status 400 (Bad Request) if the gooseTokenDTO is not valid,
     * or with status 500 (Internal Server Error) if the gooseTokenDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/goose-tokens")
    @Timed
    public ResponseEntity<GooseTokenDTO> updateGooseToken(@RequestBody GooseTokenDTO gooseTokenDTO) throws URISyntaxException {
        log.debug("REST request to update GooseToken : {}", gooseTokenDTO);
        if (gooseTokenDTO.getId() == null) {
            return createGooseToken(gooseTokenDTO);
        }
        GooseTokenDTO result = gooseTokenService.save(gooseTokenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gooseTokenDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /goose-tokens : get all the gooseTokens.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of gooseTokens in body
     */
    @GetMapping("/goose-tokens")
    @Timed
    public ResponseEntity<List<GooseTokenDTO>> getAllGooseTokens(Pageable pageable) {
        log.debug("REST request to get a page of GooseTokens");
        Page<GooseTokenDTO> page = gooseTokenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/goose-tokens");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /goose-tokens/:id : get the "id" gooseToken.
     *
     * @param id the id of the gooseTokenDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gooseTokenDTO, or with status 404 (Not Found)
     */
    @GetMapping("/goose-tokens/{id}")
    @Timed
    public ResponseEntity<GooseTokenDTO> getGooseToken(@PathVariable Long id) {
        log.debug("REST request to get GooseToken : {}", id);
        GooseTokenDTO gooseTokenDTO = gooseTokenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gooseTokenDTO));
    }

    /**
     * DELETE  /goose-tokens/:id : delete the "id" gooseToken.
     *
     * @param id the id of the gooseTokenDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/goose-tokens/{id}")
    @Timed
    public ResponseEntity<Void> deleteGooseToken(@PathVariable Long id) {
        log.debug("REST request to delete GooseToken : {}", id);
        gooseTokenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/goose-tokens?query=:query : search for the gooseToken corresponding
     * to the query.
     *
     * @param query the query of the gooseToken search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/goose-tokens")
    @Timed
    public ResponseEntity<List<GooseTokenDTO>> searchGooseTokens(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GooseTokens for query {}", query);
        Page<GooseTokenDTO> page = gooseTokenService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/goose-tokens");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
