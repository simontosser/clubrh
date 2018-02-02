package com.poleemploi.cvtheque.repository;

import com.poleemploi.cvtheque.domain.RecrutementProfil;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RecrutementProfil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecrutementProfilRepository extends JpaRepository<RecrutementProfil, Long> {

}
