package com.poleemploi.cvtheque.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the DocumentProfil entity.
 */
public class DocumentProfilDTO implements Serializable {

    private Long id;

    @NotNull
    @Lob
    private byte[] documentFile;
    private String documentFileContentType;

    private Long shareProfilId;

    private Long recrutementProfilId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getDocumentFile() {
        return documentFile;
    }

    public void setDocumentFile(byte[] documentFile) {
        this.documentFile = documentFile;
    }

    public String getDocumentFileContentType() {
        return documentFileContentType;
    }

    public void setDocumentFileContentType(String documentFileContentType) {
        this.documentFileContentType = documentFileContentType;
    }

    public Long getShareProfilId() {
        return shareProfilId;
    }

    public void setShareProfilId(Long shareProfilId) {
        this.shareProfilId = shareProfilId;
    }

    public Long getRecrutementProfilId() {
        return recrutementProfilId;
    }

    public void setRecrutementProfilId(Long recrutementProfilId) {
        this.recrutementProfilId = recrutementProfilId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DocumentProfilDTO documentProfilDTO = (DocumentProfilDTO) o;
        if(documentProfilDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documentProfilDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocumentProfilDTO{" +
            "id=" + getId() +
            ", documentFile='" + getDocumentFile() + "'" +
            "}";
    }
}
