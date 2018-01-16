package com.virtualsoundnw.sftpbot.repository;

import com.virtualsoundnw.sftpbot.domain.SftpTestCase;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SftpTestCase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SftpTestCaseRepository extends JpaRepository<SftpTestCase, Long> {

}
