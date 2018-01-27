package com.adloveyou.ms.goosegame.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.goosegame.service.GooseWinService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.goosegame.service.dto.GooseWinDTO;
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
 * REST controller for managing GooseWin.
 */
@RestController
@RequestMapping("/api")
public class GooseWinResource {

    private final Logger log = LoggerFactory.getLogger(GooseWinResource.class);

    private static final String ENTITY_NAME = "gooseWin";

    private final GooseWinService gooseWinService;

    public GooseWinResource(GooseWinService gooseWinService) {
        this.gooseWinService = gooseWinService;
    }

    /**
     * POST  /goose-wins : Create a new gooseWin.
     *
     * @param gooseWinDTO the gooseWinDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gooseWinDTO, or with status 400 (Bad Request) if the gooseWin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/goose-wins")
    @Timed
    public ResponseEntity<GooseWinDTO> createGooseWin(@RequestBody GooseWinDTO gooseWinDTO) throws URISyntaxException {
        log.debug("REST request to save GooseWin : {}", gooseWinDTO);
        if (gooseWinDTO.getId() != null) {
            throw new BadRequestAlertException("A new gooseWin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GooseWinDTO result = gooseWinService.save(gooseWinDTO);
        return ResponseEntity.created(new URI("/api/goose-wins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /goose-wins : Updates an existing gooseWin.
     *
     * @param gooseWinDTO the gooseWinDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gooseWinDTO,
     * or with status 400 (Bad Request) if the gooseWinDTO is not valid,
     * or with status 500 (Internal Server Error) if the gooseWinDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/goose-wins")
    @Timed
    public ResponseEntity<GooseWinDTO> updateGooseWin(@RequestBody GooseWinDTO gooseWinDTO) throws URISyntaxException {
        log.debug("REST request to update GooseWin : {}", gooseWinDTO);
        if (gooseWinDTO.getId() == null) {
            return createGooseWin(gooseWinDTO);
        }
        GooseWinDTO result = gooseWinService.save(gooseWinDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gooseWinDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /goose-wins : get all the gooseWins.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of gooseWins in body
     */
    @GetMapping("/goose-wins")
    @Timed
    public ResponseEntity<List<GooseWinDTO>> getAllGooseWins(Pageable pageable) {
        log.debug("REST request to get a page of GooseWins");
        Page<GooseWinDTO> page = gooseWinService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/goose-wins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /goose-wins/:id : get the "id" gooseWin.
     *
     * @param id the id of the gooseWinDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gooseWinDTO, or with status 404 (Not Found)
     */
    @GetMapping("/goose-wins/{id}")
    @Timed
    public ResponseEntity<GooseWinDTO> getGooseWin(@PathVariable Long id) {
        log.debug("REST request to get GooseWin : {}", id);
        GooseWinDTO gooseWinDTO = gooseWinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gooseWinDTO));
    }

    /**
     * DELETE  /goose-wins/:id : delete the "id" gooseWin.
     *
     * @param id the id of the gooseWinDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/goose-wins/{id}")
    @Timed
    public ResponseEntity<Void> deleteGooseWin(@PathVariable Long id) {
        log.debug("REST request to delete GooseWin : {}", id);
        gooseWinService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/goose-wins?query=:query : search for the gooseWin corresponding
     * to the query.
     *
     * @param query the query of the gooseWin search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/goose-wins")
    @Timed
    public ResponseEntity<List<GooseWinDTO>> searchGooseWins(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GooseWins for query {}", query);
        Page<GooseWinDTO> page = gooseWinService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/goose-wins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
