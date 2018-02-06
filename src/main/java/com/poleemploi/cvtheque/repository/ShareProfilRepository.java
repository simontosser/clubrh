package com.poleemploi.cvtheque.repository;

import com.poleemploi.cvtheque.domain.Company;
import com.poleemploi.cvtheque.domain.ShareProfil;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ShareProfil entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface ShareProfilRepository extends JpaRepository<ShareProfil, Long> {

	Page<ShareProfil> findAllByCompany(Pageable pageable, Company company);
	
	Page<ShareProfil> findAllByCompanyNot(Pageable pageable, Company company);

}
