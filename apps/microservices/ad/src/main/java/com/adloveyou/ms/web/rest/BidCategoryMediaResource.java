package com.adloveyou.ms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.service.BidCategoryMediaService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.service.dto.BidCategoryMediaDTO;
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
 * REST controller for managing BidCategoryMedia.
 */
@RestController
@RequestMapping("/api")
public class BidCategoryMediaResource {

    private final Logger log = LoggerFactory.getLogger(BidCategoryMediaResource.class);

    private static final String ENTITY_NAME = "bidCategoryMedia";

    private final BidCategoryMediaService bidCategoryMediaService;

    public BidCategoryMediaResource(BidCategoryMediaService bidCategoryMediaService) {
        this.bidCategoryMediaService = bidCategoryMediaService;
    }

    /**
     * POST  /bid-category-medias : Create a new bidCategoryMedia.
     *
     * @param bidCategoryMediaDTO the bidCategoryMediaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bidCategoryMediaDTO, or with status 400 (Bad Request) if the bidCategoryMedia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bid-category-medias")
    @Timed
    public ResponseEntity<BidCategoryMediaDTO> createBidCategoryMedia(@Valid @RequestBody BidCategoryMediaDTO bidCategoryMediaDTO) throws URISyntaxException {
        log.debug("REST request to save BidCategoryMedia : {}", bidCategoryMediaDTO);
        if (bidCategoryMediaDTO.getId() != null) {
            throw new BadRequestAlertException("A new bidCategoryMedia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BidCategoryMediaDTO result = bidCategoryMediaService.save(bidCategoryMediaDTO);
        return ResponseEntity.created(new URI("/api/bid-category-medias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bid-category-medias : Updates an existing bidCategoryMedia.
     *
     * @param bidCategoryMediaDTO the bidCategoryMediaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bidCategoryMediaDTO,
     * or with status 400 (Bad Request) if the bidCategoryMediaDTO is not valid,
     * or with status 500 (Internal Server Error) if the bidCategoryMediaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bid-category-medias")
    @Timed
    public ResponseEntity<BidCategoryMediaDTO> updateBidCategoryMedia(@Valid @RequestBody BidCategoryMediaDTO bidCategoryMediaDTO) throws URISyntaxException {
        log.debug("REST request to update BidCategoryMedia : {}", bidCategoryMediaDTO);
        if (bidCategoryMediaDTO.getId() == null) {
            return createBidCategoryMedia(bidCategoryMediaDTO);
        }
        BidCategoryMediaDTO result = bidCategoryMediaService.save(bidCategoryMediaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bidCategoryMediaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bid-category-medias : get all the bidCategoryMedias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bidCategoryMedias in body
     */
    @GetMapping("/bid-category-medias")
    @Timed
    public ResponseEntity<List<BidCategoryMediaDTO>> getAllBidCategoryMedias(Pageable pageable) {
        log.debug("REST request to get a page of BidCategoryMedias");
        Page<BidCategoryMediaDTO> page = bidCategoryMediaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bid-category-medias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bid-category-medias/:id : get the "id" bidCategoryMedia.
     *
     * @param id the id of the bidCategoryMediaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bidCategoryMediaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bid-category-medias/{id}")
    @Timed
    public ResponseEntity<BidCategoryMediaDTO> getBidCategoryMedia(@PathVariable Long id) {
        log.debug("REST request to get BidCategoryMedia : {}", id);
        BidCategoryMediaDTO bidCategoryMediaDTO = bidCategoryMediaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bidCategoryMediaDTO));
    }

    /**
     * DELETE  /bid-category-medias/:id : delete the "id" bidCategoryMedia.
     *
     * @param id the id of the bidCategoryMediaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bid-category-medias/{id}")
    @Timed
    public ResponseEntity<Void> deleteBidCategoryMedia(@PathVariable Long id) {
        log.debug("REST request to delete BidCategoryMedia : {}", id);
        bidCategoryMediaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/bid-category-medias?query=:query : search for the bidCategoryMedia corresponding
     * to the query.
     *
     * @param query the query of the bidCategoryMedia search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/bid-category-medias")
    @Timed
    public ResponseEntity<List<BidCategoryMediaDTO>> searchBidCategoryMedias(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BidCategoryMedias for query {}", query);
        Page<BidCategoryMediaDTO> page = bidCategoryMediaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/bid-category-medias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
