package com.virtualsoundnw.sftpbot.service.mapper;

import com.virtualsoundnw.sftpbot.domain.*;
import com.virtualsoundnw.sftpbot.service.dto.SftpTestCaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SftpTestCase and its DTO SftpTestCaseDTO.
 */
@Mapper(componentModel = "spring", uses = {SftprootMapper.class})
public interface SftpTestCaseMapper extends EntityMapper<SftpTestCaseDTO, SftpTestCase> {

    @Mapping(source = "sftproot.id", target = "sftprootId")
    SftpTestCaseDTO toDto(SftpTestCase sftpTestCase);

    @Mapping(source = "sftprootId", target = "sftproot")
    SftpTestCase toEntity(SftpTestCaseDTO sftpTestCaseDTO);

    default SftpTestCase fromId(Long id) {
        if (id == null) {
            return null;
        }
        SftpTestCase sftpTestCase = new SftpTestCase();
        sftpTestCase.setId(id);
        return sftpTestCase;
    }
}
