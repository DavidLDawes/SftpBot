package com.virtualsoundnw.sftpbot.service.mapper;

import com.virtualsoundnw.sftpbot.domain.*;
import com.virtualsoundnw.sftpbot.service.dto.SftprootDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Sftproot and its DTO SftprootDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SftprootMapper extends EntityMapper<SftprootDTO, Sftproot> {


    @Mapping(target = "sftpTestCases", ignore = true)
    Sftproot toEntity(SftprootDTO sftprootDTO);

    default Sftproot fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sftproot sftproot = new Sftproot();
        sftproot.setId(id);
        return sftproot;
    }
}
