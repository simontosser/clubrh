package com.poleemploi.cvtheque.service.mapper;

import com.poleemploi.cvtheque.domain.*;
import com.poleemploi.cvtheque.service.dto.ShareProfilDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShareProfil and its DTO ShareProfilDTO.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class, DocumentProfilMapper.class})
public interface ShareProfilMapper extends EntityMapper<ShareProfilDTO, ShareProfil> {

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    ShareProfilDTO toDto(ShareProfil shareProfil);

    @Mapping(source = "companyId", target = "company")
    ShareProfil toEntity(ShareProfilDTO shareProfilDTO);

    default ShareProfil fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShareProfil shareProfil = new ShareProfil();
        shareProfil.setId(id);
        return shareProfil;
    }
}
