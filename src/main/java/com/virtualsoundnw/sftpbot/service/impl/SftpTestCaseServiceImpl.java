package com.virtualsoundnw.sftpbot.service.impl;

import com.virtualsoundnw.sftpbot.service.SftpTestCaseService;
import com.virtualsoundnw.sftpbot.domain.SftpTestCase;
import com.virtualsoundnw.sftpbot.repository.SftpTestCaseRepository;
import com.virtualsoundnw.sftpbot.service.dto.SftpTestCaseDTO;
import com.virtualsoundnw.sftpbot.service.mapper.SftpTestCaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SftpTestCase.
 */
@Service
@Transactional
public class SftpTestCaseServiceImpl implements SftpTestCaseService {

    private final Logger log = LoggerFactory.getLogger(SftpTestCaseServiceImpl.class);

    private final SftpTestCaseRepository sftpTestCaseRepository;

    private final SftpTestCaseMapper sftpTestCaseMapper;

    public SftpTestCaseServiceImpl(SftpTestCaseRepository sftpTestCaseRepository, SftpTestCaseMapper sftpTestCaseMapper) {
        this.sftpTestCaseRepository = sftpTestCaseRepository;
        this.sftpTestCaseMapper = sftpTestCaseMapper;
    }

    /**
     * Save a sftpTestCase.
     *
     * @param sftpTestCaseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SftpTestCaseDTO save(SftpTestCaseDTO sftpTestCaseDTO) {
        log.debug("Request to save SftpTestCase : {}", sftpTestCaseDTO);
        SftpTestCase sftpTestCase = sftpTestCaseMapper.toEntity(sftpTestCaseDTO);
        sftpTestCase = sftpTestCaseRepository.save(sftpTestCase);
        return sftpTestCaseMapper.toDto(sftpTestCase);
    }

    /**
     * Get all the sftpTestCases.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SftpTestCaseDTO> findAll() {
        log.debug("Request to get all SftpTestCases");
        return sftpTestCaseRepository.findAll().stream()
            .map(sftpTestCaseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one sftpTestCase by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SftpTestCaseDTO findOne(Long id) {
        log.debug("Request to get SftpTestCase : {}", id);
        SftpTestCase sftpTestCase = sftpTestCaseRepository.findOne(id);
        return sftpTestCaseMapper.toDto(sftpTestCase);
    }

    /**
     * Delete the sftpTestCase by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SftpTestCase : {}", id);
        sftpTestCaseRepository.delete(id);
    }
}
