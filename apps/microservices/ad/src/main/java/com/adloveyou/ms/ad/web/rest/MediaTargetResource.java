package com.adloveyou.ms.ad.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.ad.service.MediaTargetService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.ad.service.dto.MediaTargetDTO;
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
 * REST controller for managing MediaTarget.
 */
@RestController
@RequestMapping("/api")
public class MediaTargetResource {

    private final Logger log = LoggerFactory.getLogger(MediaTargetResource.class);

    private static final String ENTITY_NAME = "mediaTarget";

    private final MediaTargetService mediaTargetService;

    public MediaTargetResource(MediaTargetService mediaTargetService) {
        this.mediaTargetService = mediaTargetService;
    }

    /**
     * POST  /media-targets : Create a new mediaTarget.
     *
     * @param mediaTargetDTO the mediaTargetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mediaTargetDTO, or with status 400 (Bad Request) if the mediaTarget has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/media-targets")
    @Timed
    public ResponseEntity<MediaTargetDTO> createMediaTarget(@Valid @RequestBody MediaTargetDTO mediaTargetDTO) throws URISyntaxException {
        log.debug("REST request to save MediaTarget : {}", mediaTargetDTO);
        if (mediaTargetDTO.getId() != null) {
            throw new BadRequestAlertException("A new mediaTarget cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MediaTargetDTO result = mediaTargetService.save(mediaTargetDTO);
        return ResponseEntity.created(new URI("/api/media-targets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /media-targets : Updates an existing mediaTarget.
     *
     * @param mediaTargetDTO the mediaTargetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mediaTargetDTO,
     * or with status 400 (Bad Request) if the mediaTargetDTO is not valid,
     * or with status 500 (Internal Server Error) if the mediaTargetDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/media-targets")
    @Timed
    public ResponseEntity<MediaTargetDTO> updateMediaTarget(@Valid @RequestBody MediaTargetDTO mediaTargetDTO) throws URISyntaxException {
        log.debug("REST request to update MediaTarget : {}", mediaTargetDTO);
        if (mediaTargetDTO.getId() == null) {
            return createMediaTarget(mediaTargetDTO);
        }
        MediaTargetDTO result = mediaTargetService.save(mediaTargetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mediaTargetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /media-targets : get all the mediaTargets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mediaTargets in body
     */
    @GetMapping("/media-targets")
    @Timed
    public ResponseEntity<List<MediaTargetDTO>> getAllMediaTargets(Pageable pageable) {
        log.debug("REST request to get a page of MediaTargets");
        Page<MediaTargetDTO> page = mediaTargetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/media-targets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /media-targets/:id : get the "id" mediaTarget.
     *
     * @param id the id of the mediaTargetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mediaTargetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/media-targets/{id}")
    @Timed
    public ResponseEntity<MediaTargetDTO> getMediaTarget(@PathVariable Long id) {
        log.debug("REST request to get MediaTarget : {}", id);
        MediaTargetDTO mediaTargetDTO = mediaTargetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mediaTargetDTO));
    }

    /**
     * DELETE  /media-targets/:id : delete the "id" mediaTarget.
     *
     * @param id the id of the mediaTargetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/media-targets/{id}")
    @Timed
    public ResponseEntity<Void> deleteMediaTarget(@PathVariable Long id) {
        log.debug("REST request to delete MediaTarget : {}", id);
        mediaTargetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/media-targets?query=:query : search for the mediaTarget corresponding
     * to the query.
     *
     * @param query the query of the mediaTarget search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/media-targets")
    @Timed
    public ResponseEntity<List<MediaTargetDTO>> searchMediaTargets(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MediaTargets for query {}", query);
        Page<MediaTargetDTO> page = mediaTargetService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/media-targets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
