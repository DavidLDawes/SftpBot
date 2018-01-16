package com.virtualsoundnw.sftpbot.service;

import com.virtualsoundnw.sftpbot.service.dto.SftprootDTO;
import java.util.List;

/**
 * Service Interface for managing Sftproot.
 */
public interface SftprootService {

    /**
     * Save a sftproot.
     *
     * @param sftprootDTO the entity to save
     * @return the persisted entity
     */
    SftprootDTO save(SftprootDTO sftprootDTO);

    /**
     * Get all the sftproots.
     *
     * @return the list of entities
     */
    List<SftprootDTO> findAll();

    /**
     * Get the "id" sftproot.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SftprootDTO findOne(Long id);

    /**
     * Delete the "id" sftproot.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
