package com.poleemploi.cvtheque.repository;

import com.poleemploi.cvtheque.domain.Address;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface AddressRepository extends JpaRepository<Address, Long> {

}
