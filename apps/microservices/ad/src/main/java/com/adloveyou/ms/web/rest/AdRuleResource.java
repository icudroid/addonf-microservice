package com.adloveyou.ms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.service.AdRuleService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.service.dto.AdRuleDTO;
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
 * REST controller for managing AdRule.
 */
@RestController
@RequestMapping("/api")
public class AdRuleResource {

    private final Logger log = LoggerFactory.getLogger(AdRuleResource.class);

    private static final String ENTITY_NAME = "adRule";

    private final AdRuleService adRuleService;

    public AdRuleResource(AdRuleService adRuleService) {
        this.adRuleService = adRuleService;
    }

    /**
     * POST  /ad-rules : Create a new adRule.
     *
     * @param adRuleDTO the adRuleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adRuleDTO, or with status 400 (Bad Request) if the adRule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ad-rules")
    @Timed
    public ResponseEntity<AdRuleDTO> createAdRule(@RequestBody AdRuleDTO adRuleDTO) throws URISyntaxException {
        log.debug("REST request to save AdRule : {}", adRuleDTO);
        if (adRuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new adRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdRuleDTO result = adRuleService.save(adRuleDTO);
        return ResponseEntity.created(new URI("/api/ad-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ad-rules : Updates an existing adRule.
     *
     * @param adRuleDTO the adRuleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adRuleDTO,
     * or with status 400 (Bad Request) if the adRuleDTO is not valid,
     * or with status 500 (Internal Server Error) if the adRuleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ad-rules")
    @Timed
    public ResponseEntity<AdRuleDTO> updateAdRule(@RequestBody AdRuleDTO adRuleDTO) throws URISyntaxException {
        log.debug("REST request to update AdRule : {}", adRuleDTO);
        if (adRuleDTO.getId() == null) {
            return createAdRule(adRuleDTO);
        }
        AdRuleDTO result = adRuleService.save(adRuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adRuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ad-rules : get all the adRules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of adRules in body
     */
    @GetMapping("/ad-rules")
    @Timed
    public ResponseEntity<List<AdRuleDTO>> getAllAdRules(Pageable pageable) {
        log.debug("REST request to get a page of AdRules");
        Page<AdRuleDTO> page = adRuleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ad-rules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ad-rules/:id : get the "id" adRule.
     *
     * @param id the id of the adRuleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adRuleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ad-rules/{id}")
    @Timed
    public ResponseEntity<AdRuleDTO> getAdRule(@PathVariable Long id) {
        log.debug("REST request to get AdRule : {}", id);
        AdRuleDTO adRuleDTO = adRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(adRuleDTO));
    }

    /**
     * DELETE  /ad-rules/:id : delete the "id" adRule.
     *
     * @param id the id of the adRuleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ad-rules/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdRule(@PathVariable Long id) {
        log.debug("REST request to delete AdRule : {}", id);
        adRuleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ad-rules?query=:query : search for the adRule corresponding
     * to the query.
     *
     * @param query the query of the adRule search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ad-rules")
    @Timed
    public ResponseEntity<List<AdRuleDTO>> searchAdRules(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AdRules for query {}", query);
        Page<AdRuleDTO> page = adRuleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ad-rules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
