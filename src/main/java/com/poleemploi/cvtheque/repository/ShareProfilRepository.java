package com.poleemploi.cvtheque.repository;

import com.poleemploi.cvtheque.domain.ShareProfil;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ShareProfil entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface ShareProfilRepository extends JpaRepository<ShareProfil, Long> {

}
