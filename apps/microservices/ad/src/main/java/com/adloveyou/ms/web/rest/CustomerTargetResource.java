package com.adloveyou.ms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adloveyou.ms.service.CustomerTargetService;
import com.adloveyou.ms.web.rest.errors.BadRequestAlertException;
import com.adloveyou.ms.web.rest.util.HeaderUtil;
import com.adloveyou.ms.web.rest.util.PaginationUtil;
import com.adloveyou.ms.service.dto.CustomerTargetDTO;
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
 * REST controller for managing CustomerTarget.
 */
@RestController
@RequestMapping("/api")
public class CustomerTargetResource {

    private final Logger log = LoggerFactory.getLogger(CustomerTargetResource.class);

    private static final String ENTITY_NAME = "customerTarget";

    private final CustomerTargetService customerTargetService;

    public CustomerTargetResource(CustomerTargetService customerTargetService) {
        this.customerTargetService = customerTargetService;
    }

    /**
     * POST  /customer-targets : Create a new customerTarget.
     *
     * @param customerTargetDTO the customerTargetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerTargetDTO, or with status 400 (Bad Request) if the customerTarget has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-targets")
    @Timed
    public ResponseEntity<CustomerTargetDTO> createCustomerTarget(@RequestBody CustomerTargetDTO customerTargetDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerTarget : {}", customerTargetDTO);
        if (customerTargetDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerTarget cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerTargetDTO result = customerTargetService.save(customerTargetDTO);
        return ResponseEntity.created(new URI("/api/customer-targets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-targets : Updates an existing customerTarget.
     *
     * @param customerTargetDTO the customerTargetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerTargetDTO,
     * or with status 400 (Bad Request) if the customerTargetDTO is not valid,
     * or with status 500 (Internal Server Error) if the customerTargetDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-targets")
    @Timed
    public ResponseEntity<CustomerTargetDTO> updateCustomerTarget(@RequestBody CustomerTargetDTO customerTargetDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerTarget : {}", customerTargetDTO);
        if (customerTargetDTO.getId() == null) {
            return createCustomerTarget(customerTargetDTO);
        }
        CustomerTargetDTO result = customerTargetService.save(customerTargetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerTargetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-targets : get all the customerTargets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of customerTargets in body
     */
    @GetMapping("/customer-targets")
    @Timed
    public ResponseEntity<List<CustomerTargetDTO>> getAllCustomerTargets(Pageable pageable) {
        log.debug("REST request to get a page of CustomerTargets");
        Page<CustomerTargetDTO> page = customerTargetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-targets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customer-targets/:id : get the "id" customerTarget.
     *
     * @param id the id of the customerTargetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerTargetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/customer-targets/{id}")
    @Timed
    public ResponseEntity<CustomerTargetDTO> getCustomerTarget(@PathVariable Long id) {
        log.debug("REST request to get CustomerTarget : {}", id);
        CustomerTargetDTO customerTargetDTO = customerTargetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerTargetDTO));
    }

    /**
     * DELETE  /customer-targets/:id : delete the "id" customerTarget.
     *
     * @param id the id of the customerTargetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-targets/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerTarget(@PathVariable Long id) {
        log.debug("REST request to delete CustomerTarget : {}", id);
        customerTargetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/customer-targets?query=:query : search for the customerTarget corresponding
     * to the query.
     *
     * @param query the query of the customerTarget search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/customer-targets")
    @Timed
    public ResponseEntity<List<CustomerTargetDTO>> searchCustomerTargets(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CustomerTargets for query {}", query);
        Page<CustomerTargetDTO> page = customerTargetService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/customer-targets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
