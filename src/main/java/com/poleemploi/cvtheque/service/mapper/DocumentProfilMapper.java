package com.poleemploi.cvtheque.service.mapper;

import com.poleemploi.cvtheque.domain.*;
import com.poleemploi.cvtheque.service.dto.DocumentProfilDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DocumentProfil and its DTO DocumentProfilDTO.
 */
@Mapper(componentModel = "spring", uses = {ShareProfilMapper.class, RecrutementProfilMapper.class})
public interface DocumentProfilMapper extends EntityMapper<DocumentProfilDTO, DocumentProfil> {

    @Mapping(source = "shareProfil.id", target = "shareProfilId")
    @Mapping(source = "recrutementProfil.id", target = "recrutementProfilId")
    DocumentProfilDTO toDto(DocumentProfil documentProfil);

    @Mapping(source = "shareProfilId", target = "shareProfil")
    @Mapping(source = "recrutementProfilId", target = "recrutementProfil")
    DocumentProfil toEntity(DocumentProfilDTO documentProfilDTO);

    default DocumentProfil fromId(Long id) {
        if (id == null) {
            return null;
        }
        DocumentProfil documentProfil = new DocumentProfil();
        documentProfil.setId(id);
        return documentProfil;
    }
}
