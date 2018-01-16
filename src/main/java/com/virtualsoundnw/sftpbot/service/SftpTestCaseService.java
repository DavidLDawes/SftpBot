package com.virtualsoundnw.sftpbot.service;

import com.virtualsoundnw.sftpbot.service.dto.SftpTestCaseDTO;
import java.util.List;

/**
 * Service Interface for managing SftpTestCase.
 */
public interface SftpTestCaseService {

    /**
     * Save a sftpTestCase.
     *
     * @param sftpTestCaseDTO the entity to save
     * @return the persisted entity
     */
    SftpTestCaseDTO save(SftpTestCaseDTO sftpTestCaseDTO);

    /**
     * Get all the sftpTestCases.
     *
     * @return the list of entities
     */
    List<SftpTestCaseDTO> findAll();

    /**
     * Get the "id" sftpTestCase.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SftpTestCaseDTO findOne(Long id);

    /**
     * Delete the "id" sftpTestCase.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
