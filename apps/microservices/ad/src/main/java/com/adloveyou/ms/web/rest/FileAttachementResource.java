package com.adloveyou.ms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.service.FileAttachementService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.service.dto.FileAttachementDTO;
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
 * REST controller for managing FileAttachement.
 */
@RestController
@RequestMapping("/api")
public class FileAttachementResource {

    private final Logger log = LoggerFactory.getLogger(FileAttachementResource.class);

    private static final String ENTITY_NAME = "fileAttachement";

    private final FileAttachementService fileAttachementService;

    public FileAttachementResource(FileAttachementService fileAttachementService) {
        this.fileAttachementService = fileAttachementService;
    }

    /**
     * POST  /file-attachements : Create a new fileAttachement.
     *
     * @param fileAttachementDTO the fileAttachementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fileAttachementDTO, or with status 400 (Bad Request) if the fileAttachement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/file-attachements")
    @Timed
    public ResponseEntity<FileAttachementDTO> createFileAttachement(@RequestBody FileAttachementDTO fileAttachementDTO) throws URISyntaxException {
        log.debug("REST request to save FileAttachement : {}", fileAttachementDTO);
        if (fileAttachementDTO.getId() != null) {
            throw new BadRequestAlertException("A new fileAttachement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileAttachementDTO result = fileAttachementService.save(fileAttachementDTO);
        return ResponseEntity.created(new URI("/api/file-attachements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /file-attachements : Updates an existing fileAttachement.
     *
     * @param fileAttachementDTO the fileAttachementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fileAttachementDTO,
     * or with status 400 (Bad Request) if the fileAttachementDTO is not valid,
     * or with status 500 (Internal Server Error) if the fileAttachementDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/file-attachements")
    @Timed
    public ResponseEntity<FileAttachementDTO> updateFileAttachement(@RequestBody FileAttachementDTO fileAttachementDTO) throws URISyntaxException {
        log.debug("REST request to update FileAttachement : {}", fileAttachementDTO);
        if (fileAttachementDTO.getId() == null) {
            return createFileAttachement(fileAttachementDTO);
        }
        FileAttachementDTO result = fileAttachementService.save(fileAttachementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fileAttachementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /file-attachements : get all the fileAttachements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fileAttachements in body
     */
    @GetMapping("/file-attachements")
    @Timed
    public ResponseEntity<List<FileAttachementDTO>> getAllFileAttachements(Pageable pageable) {
        log.debug("REST request to get a page of FileAttachements");
        Page<FileAttachementDTO> page = fileAttachementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/file-attachements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /file-attachements/:id : get the "id" fileAttachement.
     *
     * @param id the id of the fileAttachementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fileAttachementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/file-attachements/{id}")
    @Timed
    public ResponseEntity<FileAttachementDTO> getFileAttachement(@PathVariable Long id) {
        log.debug("REST request to get FileAttachement : {}", id);
        FileAttachementDTO fileAttachementDTO = fileAttachementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fileAttachementDTO));
    }

    /**
     * DELETE  /file-attachements/:id : delete the "id" fileAttachement.
     *
     * @param id the id of the fileAttachementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/file-attachements/{id}")
    @Timed
    public ResponseEntity<Void> deleteFileAttachement(@PathVariable Long id) {
        log.debug("REST request to delete FileAttachement : {}", id);
        fileAttachementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/file-attachements?query=:query : search for the fileAttachement corresponding
     * to the query.
     *
     * @param query the query of the fileAttachement search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/file-attachements")
    @Timed
    public ResponseEntity<List<FileAttachementDTO>> searchFileAttachements(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FileAttachements for query {}", query);
        Page<FileAttachementDTO> page = fileAttachementService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/file-attachements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
