package com.poleemploi.cvtheque.repository;

import com.poleemploi.cvtheque.domain.Company;
import com.poleemploi.cvtheque.domain.RecrutementProfil;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RecrutementProfil entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface RecrutementProfilRepository extends JpaRepository<RecrutementProfil, Long> {
	
	Page<RecrutementProfil> findAllByCompany(Pageable pageable, Company company);
	
	Page<RecrutementProfil> findAllByCompanyNot(Pageable pageable, Company company);
}
