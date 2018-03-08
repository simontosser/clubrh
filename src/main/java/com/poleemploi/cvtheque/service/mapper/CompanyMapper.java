package com.poleemploi.cvtheque.service.mapper;

import com.poleemploi.cvtheque.domain.*;
import com.poleemploi.cvtheque.service.dto.CompanyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Company and its DTO CompanyDTO.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {

    CompanyDTO toDto(Company company);

    @Mapping(target = "shareProfils", ignore = true)
    @Mapping(target = "recrutementProfils", ignore = true)
    Company toEntity(CompanyDTO companyDTO);

    default Company fromId(Long id) {
        if (id == null) {
            return null;
        }
        Company company = new Company();
        company.setId(id);
        return company;
    }
}
