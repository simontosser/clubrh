package com.poleemploi.cvtheque.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A RecrutementProfil.
 */
@Entity
@Table(name = "recrutement_profil")
@Document(indexName = "recrutementprofil")
public class RecrutementProfil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "job_title", length = 256, nullable = false)
    private String jobTitle;

    @Size(min = 1, max = 50)
    @Column(name = "context", length = 50)
    private String context;

    @Size(min = 1, max = 256)
    @Column(name = "finaly_object", length = 256)
    private String finalyObject;

    @Size(min = 1, max = 2048)
    @Column(name = "main_activity", length = 2048)
    private String mainActivity;

    @Size(min = 1, max = 2048)
    @Column(name = "main_skill", length = 2048)
    private String mainSkill;

    @Size(min = 1, max = 2048)
    @Column(name = "knownledge", length = 2048)
    private String knownledge;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Size(min = 1, max = 50)
    @Column(name = "consideration", length = 50)
    private String consideration;

    @Size(min = 1, max = 50)
    @Column(name = "remind", length = 50)
    private String remind;

    @Size(min = 1, max = 2048)
    @Column(name = "jhi_comment", length = 2048)
    private String comment;

    @OneToMany(mappedBy = "recrutementProfil", cascade = CascadeType.ALL)
    private Set<DocumentProfil> documentProfils = new HashSet<>();

    @ManyToOne
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public RecrutementProfil jobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getContext() {
        return context;
    }

    public RecrutementProfil context(String context) {
        this.context = context;
        return this;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getFinalyObject() {
        return finalyObject;
    }

    public RecrutementProfil finalyObject(String finalyObject) {
        this.finalyObject = finalyObject;
        return this;
    }

    public void setFinalyObject(String finalyObject) {
        this.finalyObject = finalyObject;
    }

    public String getMainActivity() {
        return mainActivity;
    }

    public RecrutementProfil mainActivity(String mainActivity) {
        this.mainActivity = mainActivity;
        return this;
    }

    public void setMainActivity(String mainActivity) {
        this.mainActivity = mainActivity;
    }

    public String getMainSkill() {
        return mainSkill;
    }

    public RecrutementProfil mainSkill(String mainSkill) {
        this.mainSkill = mainSkill;
        return this;
    }

    public void setMainSkill(String mainSkill) {
        this.mainSkill = mainSkill;
    }

    public String getKnownledge() {
        return knownledge;
    }

    public RecrutementProfil knownledge(String knownledge) {
        this.knownledge = knownledge;
        return this;
    }

    public void setKnownledge(String knownledge) {
        this.knownledge = knownledge;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public RecrutementProfil deadline(LocalDate deadline) {
        this.deadline = deadline;
        return this;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getConsideration() {
        return consideration;
    }

    public RecrutementProfil consideration(String consideration) {
        this.consideration = consideration;
        return this;
    }

    public void setConsideration(String consideration) {
        this.consideration = consideration;
    }

    public String getRemind() {
        return remind;
    }

    public RecrutementProfil remind(String remind) {
        this.remind = remind;
        return this;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public String getComment() {
        return comment;
    }

    public RecrutementProfil comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Set<DocumentProfil> getDocumentProfils() {
        return documentProfils;
    }

    public RecrutementProfil documentProfils(Set<DocumentProfil> documentProfils) {
        this.documentProfils = documentProfils;
        return this;
    }

    public RecrutementProfil addDocumentProfils(DocumentProfil documentProfil) {
        this.documentProfils.add(documentProfil);
        documentProfil.setRecrutementProfil(this);
        return this;
    }

    public RecrutementProfil removeDocumentProfils(DocumentProfil documentProfil) {
        this.documentProfils.remove(documentProfil);
        documentProfil.setRecrutementProfil(null);
        return this;
    }

    public void setDocumentProfils(Set<DocumentProfil> documentProfils) {
        this.documentProfils = documentProfils;
    }

    public Company getCompany() {
        return company;
    }

    public RecrutementProfil company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
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
        RecrutementProfil recrutementProfil = (RecrutementProfil) o;
        if (recrutementProfil.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recrutementProfil.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecrutementProfil{" +
            "id=" + getId() +
            ", jobTitle='" + getJobTitle() + "'" +
            ", context='" + getContext() + "'" +
            ", finalyObject='" + getFinalyObject() + "'" +
            ", mainActivity='" + getMainActivity() + "'" +
            ", mainSkill='" + getMainSkill() + "'" +
            ", knownledge='" + getKnownledge() + "'" +
            ", deadline='" + getDeadline() + "'" +
            ", consideration='" + getConsideration() + "'" +
            ", remind='" + getRemind() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
