package com.poleemploi.cvtheque.service.mapper;

import com.poleemploi.cvtheque.domain.*;
import com.poleemploi.cvtheque.service.dto.RecrutementProfilDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RecrutementProfil and its DTO RecrutementProfilDTO.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface RecrutementProfilMapper extends EntityMapper<RecrutementProfilDTO, RecrutementProfil> {

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    RecrutementProfilDTO toDto(RecrutementProfil recrutementProfil);

    @Mapping(target = "documentProfils", ignore = true)
    @Mapping(source = "companyId", target = "company")
    RecrutementProfil toEntity(RecrutementProfilDTO recrutementProfilDTO);

    default RecrutementProfil fromId(Long id) {
        if (id == null) {
            return null;
        }
        RecrutementProfil recrutementProfil = new RecrutementProfil();
        recrutementProfil.setId(id);
        return recrutementProfil;
    }
}
