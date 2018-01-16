package com.virtualsoundnw.sftpbot.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.virtualsoundnw.sftpbot.service.SftprootService;
import com.virtualsoundnw.sftpbot.web.rest.errors.BadRequestAlertException;
import com.virtualsoundnw.sftpbot.web.rest.util.HeaderUtil;
import com.virtualsoundnw.sftpbot.service.dto.SftprootDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Sftproot.
 */
@RestController
@RequestMapping("/api")
public class SftprootResource {

    private final Logger log = LoggerFactory.getLogger(SftprootResource.class);

    private static final String ENTITY_NAME = "sftproot";

    private final SftprootService sftprootService;

    public SftprootResource(SftprootService sftprootService) {
        this.sftprootService = sftprootService;
    }

    /**
     * POST  /sftproots : Create a new sftproot.
     *
     * @param sftprootDTO the sftprootDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sftprootDTO, or with status 400 (Bad Request) if the sftproot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sftproots")
    @Timed
    public ResponseEntity<SftprootDTO> createSftproot(@RequestBody SftprootDTO sftprootDTO) throws URISyntaxException {
        log.debug("REST request to save Sftproot : {}", sftprootDTO);
        if (sftprootDTO.getId() != null) {
            throw new BadRequestAlertException("A new sftproot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SftprootDTO result = sftprootService.save(sftprootDTO);
        return ResponseEntity.created(new URI("/api/sftproots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sftproots : Updates an existing sftproot.
     *
     * @param sftprootDTO the sftprootDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sftprootDTO,
     * or with status 400 (Bad Request) if the sftprootDTO is not valid,
     * or with status 500 (Internal Server Error) if the sftprootDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sftproots")
    @Timed
    public ResponseEntity<SftprootDTO> updateSftproot(@RequestBody SftprootDTO sftprootDTO) throws URISyntaxException {
        log.debug("REST request to update Sftproot : {}", sftprootDTO);
        if (sftprootDTO.getId() == null) {
            return createSftproot(sftprootDTO);
        }
        SftprootDTO result = sftprootService.save(sftprootDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sftprootDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sftproots : get all the sftproots.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sftproots in body
     */
    @GetMapping("/sftproots")
    @Timed
    public List<SftprootDTO> getAllSftproots() {
        log.debug("REST request to get all Sftproots");
        return sftprootService.findAll();
        }

    /**
     * GET  /sftproots/:id : get the "id" sftproot.
     *
     * @param id the id of the sftprootDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sftprootDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sftproots/{id}")
    @Timed
    public ResponseEntity<SftprootDTO> getSftproot(@PathVariable Long id) {
        log.debug("REST request to get Sftproot : {}", id);
        SftprootDTO sftprootDTO = sftprootService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sftprootDTO));
    }

    /**
     * DELETE  /sftproots/:id : delete the "id" sftproot.
     *
     * @param id the id of the sftprootDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sftproots/{id}")
    @Timed
    public ResponseEntity<Void> deleteSftproot(@PathVariable Long id) {
        log.debug("REST request to delete Sftproot : {}", id);
        sftprootService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
