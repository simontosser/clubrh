package com.poleemploi.cvtheque.repository;

import com.poleemploi.cvtheque.domain.DocumentProfil;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DocumentProfil entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface DocumentProfilRepository extends JpaRepository<DocumentProfil, Long> {

}
