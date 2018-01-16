package com.virtualsoundnw.sftpbot.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.virtualsoundnw.sftpbot.service.SftpTestCaseService;
import com.virtualsoundnw.sftpbot.web.rest.errors.BadRequestAlertException;
import com.virtualsoundnw.sftpbot.web.rest.util.HeaderUtil;
import com.virtualsoundnw.sftpbot.service.dto.SftpTestCaseDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SftpTestCase.
 */
@RestController
@RequestMapping("/api")
public class SftpTestCaseResource {

    private final Logger log = LoggerFactory.getLogger(SftpTestCaseResource.class);

    private static final String ENTITY_NAME = "sftpTestCase";

    private final SftpTestCaseService sftpTestCaseService;

    public SftpTestCaseResource(SftpTestCaseService sftpTestCaseService) {
        this.sftpTestCaseService = sftpTestCaseService;
    }

    /**
     * POST  /sftp-test-cases : Create a new sftpTestCase.
     *
     * @param sftpTestCaseDTO the sftpTestCaseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sftpTestCaseDTO, or with status 400 (Bad Request) if the sftpTestCase has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sftp-test-cases")
    @Timed
    public ResponseEntity<SftpTestCaseDTO> createSftpTestCase(@Valid @RequestBody SftpTestCaseDTO sftpTestCaseDTO) throws URISyntaxException {
        log.debug("REST request to save SftpTestCase : {}", sftpTestCaseDTO);
        if (sftpTestCaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new sftpTestCase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SftpTestCaseDTO result = sftpTestCaseService.save(sftpTestCaseDTO);
        return ResponseEntity.created(new URI("/api/sftp-test-cases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sftp-test-cases : Updates an existing sftpTestCase.
     *
     * @param sftpTestCaseDTO the sftpTestCaseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sftpTestCaseDTO,
     * or with status 400 (Bad Request) if the sftpTestCaseDTO is not valid,
     * or with status 500 (Internal Server Error) if the sftpTestCaseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sftp-test-cases")
    @Timed
    public ResponseEntity<SftpTestCaseDTO> updateSftpTestCase(@Valid @RequestBody SftpTestCaseDTO sftpTestCaseDTO) throws URISyntaxException {
        log.debug("REST request to update SftpTestCase : {}", sftpTestCaseDTO);
        if (sftpTestCaseDTO.getId() == null) {
            return createSftpTestCase(sftpTestCaseDTO);
        }
        SftpTestCaseDTO result = sftpTestCaseService.save(sftpTestCaseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sftpTestCaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sftp-test-cases : get all the sftpTestCases.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sftpTestCases in body
     */
    @GetMapping("/sftp-test-cases")
    @Timed
    public List<SftpTestCaseDTO> getAllSftpTestCases() {
        log.debug("REST request to get all SftpTestCases");
        return sftpTestCaseService.findAll();
        }

    /**
     * GET  /sftp-test-cases/:id : get the "id" sftpTestCase.
     *
     * @param id the id of the sftpTestCaseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sftpTestCaseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sftp-test-cases/{id}")
    @Timed
    public ResponseEntity<SftpTestCaseDTO> getSftpTestCase(@PathVariable Long id) {
        log.debug("REST request to get SftpTestCase : {}", id);
        SftpTestCaseDTO sftpTestCaseDTO = sftpTestCaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sftpTestCaseDTO));
    }

    /**
     * DELETE  /sftp-test-cases/:id : delete the "id" sftpTestCase.
     *
     * @param id the id of the sftpTestCaseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sftp-test-cases/{id}")
    @Timed
    public ResponseEntity<Void> deleteSftpTestCase(@PathVariable Long id) {
        log.debug("REST request to delete SftpTestCase : {}", id);
        sftpTestCaseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
