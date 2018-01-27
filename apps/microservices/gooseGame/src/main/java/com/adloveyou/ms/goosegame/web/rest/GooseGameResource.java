package com.adloveyou.ms.goosegame.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.goosegame.service.GooseGameService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.goosegame.service.dto.GooseGameDTO;
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
 * REST controller for managing GooseGame.
 */
@RestController
@RequestMapping("/api")
public class GooseGameResource {

    private final Logger log = LoggerFactory.getLogger(GooseGameResource.class);

    private static final String ENTITY_NAME = "gooseGame";

    private final GooseGameService gooseGameService;

    public GooseGameResource(GooseGameService gooseGameService) {
        this.gooseGameService = gooseGameService;
    }

    /**
     * POST  /goose-games : Create a new gooseGame.
     *
     * @param gooseGameDTO the gooseGameDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gooseGameDTO, or with status 400 (Bad Request) if the gooseGame has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/goose-games")
    @Timed
    public ResponseEntity<GooseGameDTO> createGooseGame(@RequestBody GooseGameDTO gooseGameDTO) throws URISyntaxException {
        log.debug("REST request to save GooseGame : {}", gooseGameDTO);
        if (gooseGameDTO.getId() != null) {
            throw new BadRequestAlertException("A new gooseGame cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GooseGameDTO result = gooseGameService.save(gooseGameDTO);
        return ResponseEntity.created(new URI("/api/goose-games/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /goose-games : Updates an existing gooseGame.
     *
     * @param gooseGameDTO the gooseGameDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gooseGameDTO,
     * or with status 400 (Bad Request) if the gooseGameDTO is not valid,
     * or with status 500 (Internal Server Error) if the gooseGameDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/goose-games")
    @Timed
    public ResponseEntity<GooseGameDTO> updateGooseGame(@RequestBody GooseGameDTO gooseGameDTO) throws URISyntaxException {
        log.debug("REST request to update GooseGame : {}", gooseGameDTO);
        if (gooseGameDTO.getId() == null) {
            return createGooseGame(gooseGameDTO);
        }
        GooseGameDTO result = gooseGameService.save(gooseGameDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gooseGameDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /goose-games : get all the gooseGames.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of gooseGames in body
     */
    @GetMapping("/goose-games")
    @Timed
    public ResponseEntity<List<GooseGameDTO>> getAllGooseGames(Pageable pageable) {
        log.debug("REST request to get a page of GooseGames");
        Page<GooseGameDTO> page = gooseGameService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/goose-games");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /goose-games/:id : get the "id" gooseGame.
     *
     * @param id the id of the gooseGameDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gooseGameDTO, or with status 404 (Not Found)
     */
    @GetMapping("/goose-games/{id}")
    @Timed
    public ResponseEntity<GooseGameDTO> getGooseGame(@PathVariable Long id) {
        log.debug("REST request to get GooseGame : {}", id);
        GooseGameDTO gooseGameDTO = gooseGameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gooseGameDTO));
    }

    /**
     * DELETE  /goose-games/:id : delete the "id" gooseGame.
     *
     * @param id the id of the gooseGameDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/goose-games/{id}")
    @Timed
    public ResponseEntity<Void> deleteGooseGame(@PathVariable Long id) {
        log.debug("REST request to delete GooseGame : {}", id);
        gooseGameService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/goose-games?query=:query : search for the gooseGame corresponding
     * to the query.
     *
     * @param query the query of the gooseGame search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/goose-games")
    @Timed
    public ResponseEntity<List<GooseGameDTO>> searchGooseGames(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GooseGames for query {}", query);
        Page<GooseGameDTO> page = gooseGameService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/goose-games");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
