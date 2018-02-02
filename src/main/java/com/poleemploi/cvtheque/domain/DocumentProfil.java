package com.poleemploi.cvtheque.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DocumentProfil.
 */
@Entity
@Table(name = "document_profil")
@Document(indexName = "documentprofil")
public class DocumentProfil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Lob
    @Column(name = "document_file", nullable = false)
    private byte[] documentFile;

    @Column(name = "document_file_content_type", nullable = false)
    private String documentFileContentType;

    @ManyToOne
    private ShareProfil shareProfil;

    @ManyToOne
    private RecrutementProfil recrutementProfil;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getDocumentFile() {
        return documentFile;
    }

    public DocumentProfil documentFile(byte[] documentFile) {
        this.documentFile = documentFile;
        return this;
    }

    public void setDocumentFile(byte[] documentFile) {
        this.documentFile = documentFile;
    }

    public String getDocumentFileContentType() {
        return documentFileContentType;
    }

    public DocumentProfil documentFileContentType(String documentFileContentType) {
        this.documentFileContentType = documentFileContentType;
        return this;
    }

    public void setDocumentFileContentType(String documentFileContentType) {
        this.documentFileContentType = documentFileContentType;
    }

    public ShareProfil getShareProfil() {
        return shareProfil;
    }

    public DocumentProfil shareProfil(ShareProfil shareProfil) {
        this.shareProfil = shareProfil;
        return this;
    }

    public void setShareProfil(ShareProfil shareProfil) {
        this.shareProfil = shareProfil;
    }

    public RecrutementProfil getRecrutementProfil() {
        return recrutementProfil;
    }

    public DocumentProfil recrutementProfil(RecrutementProfil recrutementProfil) {
        this.recrutementProfil = recrutementProfil;
        return this;
    }

    public void setRecrutementProfil(RecrutementProfil recrutementProfil) {
        this.recrutementProfil = recrutementProfil;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DocumentProfil documentProfil = (DocumentProfil) o;
        if (documentProfil.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documentProfil.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocumentProfil{" +
            "id=" + getId() +
            ", documentFile='" + getDocumentFile() + "'" +
            ", documentFileContentType='" + getDocumentFileContentType() + "'" +
            "}";
    }
}
