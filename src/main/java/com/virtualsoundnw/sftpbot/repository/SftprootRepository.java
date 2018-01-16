package com.virtualsoundnw.sftpbot.repository;

import com.virtualsoundnw.sftpbot.domain.Sftproot;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Sftproot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SftprootRepository extends JpaRepository<Sftproot, Long> {

}
