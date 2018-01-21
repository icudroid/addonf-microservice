package com.adloveyou.ms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.service.MediaUserService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.service.dto.MediaUserDTO;
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
 * REST controller for managing MediaUser.
 */
@RestController
@RequestMapping("/api")
public class MediaUserResource {

    private final Logger log = LoggerFactory.getLogger(MediaUserResource.class);

    private static final String ENTITY_NAME = "mediaUser";

    private final MediaUserService mediaUserService;

    public MediaUserResource(MediaUserService mediaUserService) {
        this.mediaUserService = mediaUserService;
    }

    /**
     * POST  /media-users : Create a new mediaUser.
     *
     * @param mediaUserDTO the mediaUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mediaUserDTO, or with status 400 (Bad Request) if the mediaUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/media-users")
    @Timed
    public ResponseEntity<MediaUserDTO> createMediaUser(@Valid @RequestBody MediaUserDTO mediaUserDTO) throws URISyntaxException {
        log.debug("REST request to save MediaUser : {}", mediaUserDTO);
        if (mediaUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new mediaUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MediaUserDTO result = mediaUserService.save(mediaUserDTO);
        return ResponseEntity.created(new URI("/api/media-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /media-users : Updates an existing mediaUser.
     *
     * @param mediaUserDTO the mediaUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mediaUserDTO,
     * or with status 400 (Bad Request) if the mediaUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the mediaUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/media-users")
    @Timed
    public ResponseEntity<MediaUserDTO> updateMediaUser(@Valid @RequestBody MediaUserDTO mediaUserDTO) throws URISyntaxException {
        log.debug("REST request to update MediaUser : {}", mediaUserDTO);
        if (mediaUserDTO.getId() == null) {
            return createMediaUser(mediaUserDTO);
        }
        MediaUserDTO result = mediaUserService.save(mediaUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mediaUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /media-users : get all the mediaUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mediaUsers in body
     */
    @GetMapping("/media-users")
    @Timed
    public ResponseEntity<List<MediaUserDTO>> getAllMediaUsers(Pageable pageable) {
        log.debug("REST request to get a page of MediaUsers");
        Page<MediaUserDTO> page = mediaUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/media-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /media-users/:id : get the "id" mediaUser.
     *
     * @param id the id of the mediaUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mediaUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/media-users/{id}")
    @Timed
    public ResponseEntity<MediaUserDTO> getMediaUser(@PathVariable Long id) {
        log.debug("REST request to get MediaUser : {}", id);
        MediaUserDTO mediaUserDTO = mediaUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mediaUserDTO));
    }

    /**
     * DELETE  /media-users/:id : delete the "id" mediaUser.
     *
     * @param id the id of the mediaUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/media-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteMediaUser(@PathVariable Long id) {
        log.debug("REST request to delete MediaUser : {}", id);
        mediaUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/media-users?query=:query : search for the mediaUser corresponding
     * to the query.
     *
     * @param query the query of the mediaUser search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/media-users")
    @Timed
    public ResponseEntity<List<MediaUserDTO>> searchMediaUsers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MediaUsers for query {}", query);
        Page<MediaUserDTO> page = mediaUserService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/media-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
