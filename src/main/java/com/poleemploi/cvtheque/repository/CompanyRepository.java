package com.poleemploi.cvtheque.repository;

import com.poleemploi.cvtheque.domain.Company;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Company entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
