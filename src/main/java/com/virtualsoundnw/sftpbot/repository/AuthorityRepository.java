package com.virtualsoundnw.sftpbot.repository;

import com.virtualsoundnw.sftpbot.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
