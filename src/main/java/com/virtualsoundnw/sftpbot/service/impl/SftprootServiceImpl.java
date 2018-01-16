package com.virtualsoundnw.sftpbot.service.impl;

import com.virtualsoundnw.sftpbot.service.SftprootService;
import com.virtualsoundnw.sftpbot.domain.Sftproot;
import com.virtualsoundnw.sftpbot.repository.SftprootRepository;
import com.virtualsoundnw.sftpbot.service.dto.SftprootDTO;
import com.virtualsoundnw.sftpbot.service.mapper.SftprootMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Sftproot.
 */
@Service
@Transactional
public class SftprootServiceImpl implements SftprootService {

    private final Logger log = LoggerFactory.getLogger(SftprootServiceImpl.class);

    private final SftprootRepository sftprootRepository;

    private final SftprootMapper sftprootMapper;

    public SftprootServiceImpl(SftprootRepository sftprootRepository, SftprootMapper sftprootMapper) {
        this.sftprootRepository = sftprootRepository;
        this.sftprootMapper = sftprootMapper;
    }

    /**
     * Save a sftproot.
     *
     * @param sftprootDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SftprootDTO save(SftprootDTO sftprootDTO) {
        log.debug("Request to save Sftproot : {}", sftprootDTO);
        Sftproot sftproot = sftprootMapper.toEntity(sftprootDTO);
        sftproot = sftprootRepository.save(sftproot);
        return sftprootMapper.toDto(sftproot);
    }

    /**
     * Get all the sftproots.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SftprootDTO> findAll() {
        log.debug("Request to get all Sftproots");
        return sftprootRepository.findAll().stream()
            .map(sftprootMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one sftproot by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SftprootDTO findOne(Long id) {
        log.debug("Request to get Sftproot : {}", id);
        Sftproot sftproot = sftprootRepository.findOne(id);
        return sftprootMapper.toDto(sftproot);
    }

    /**
     * Delete the sftproot by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sftproot : {}", id);
        sftprootRepository.delete(id);
    }
}
