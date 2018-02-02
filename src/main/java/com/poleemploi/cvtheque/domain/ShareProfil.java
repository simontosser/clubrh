package com.poleemploi.cvtheque.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ShareProfil.
 */
@Entity
@Table(name = "share_profil")
@Document(indexName = "shareprofil")
public class ShareProfil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "target_job", length = 256, nullable = false)
    private String targetJob;

    @Size(min = 1, max = 256)
    @Column(name = "skill_shield", length = 256)
    private String skillShield;

    @Size(min = 1, max = 2048)
    @Column(name = "main_activity", length = 2048)
    private String mainActivity;

    @Size(min = 1, max = 2048)
    @Column(name = "carry_activity", length = 2048)
    private String carryActivity;

    @Size(min = 1, max = 2048)
    @Column(name = "skill_expends", length = 2048)
    private String skillExpends;

    @Size(min = 1, max = 50)
    @Column(name = "context", length = 50)
    private String context;

    @Size(min = 1, max = 256)
    @Column(name = "collabo_type", length = 256)
    private String collaboType;

    @Size(min = 1, max = 50)
    @Column(name = "consideration", length = 50)
    private String consideration;

    @Size(min = 1, max = 2048)
    @Column(name = "jhi_comment", length = 2048)
    private String comment;

    @OneToMany(mappedBy = "shareProfil")
    @JsonIgnore
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

    public String getTargetJob() {
        return targetJob;
    }

    public ShareProfil targetJob(String targetJob) {
        this.targetJob = targetJob;
        return this;
    }

    public void setTargetJob(String targetJob) {
        this.targetJob = targetJob;
    }

    public String getSkillShield() {
        return skillShield;
    }

    public ShareProfil skillShield(String skillShield) {
        this.skillShield = skillShield;
        return this;
    }

    public void setSkillShield(String skillShield) {
        this.skillShield = skillShield;
    }

    public String getMainActivity() {
        return mainActivity;
    }

    public ShareProfil mainActivity(String mainActivity) {
        this.mainActivity = mainActivity;
        return this;
    }

    public void setMainActivity(String mainActivity) {
        this.mainActivity = mainActivity;
    }

    public String getCarryActivity() {
        return carryActivity;
    }

    public ShareProfil carryActivity(String carryActivity) {
        this.carryActivity = carryActivity;
        return this;
    }

    public void setCarryActivity(String carryActivity) {
        this.carryActivity = carryActivity;
    }

    public String getSkillExpends() {
        return skillExpends;
    }

    public ShareProfil skillExpends(String skillExpends) {
        this.skillExpends = skillExpends;
        return this;
    }

    public void setSkillExpends(String skillExpends) {
        this.skillExpends = skillExpends;
    }

    public String getContext() {
        return context;
    }

    public ShareProfil context(String context) {
        this.context = context;
        return this;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getCollaboType() {
        return collaboType;
    }

    public ShareProfil collaboType(String collaboType) {
        this.collaboType = collaboType;
        return this;
    }

    public void setCollaboType(String collaboType) {
        this.collaboType = collaboType;
    }

    public String getConsideration() {
        return consideration;
    }

    public ShareProfil consideration(String consideration) {
        this.consideration = consideration;
        return this;
    }

    public void setConsideration(String consideration) {
        this.consideration = consideration;
    }

    public String getComment() {
        return comment;
    }

    public ShareProfil comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Set<DocumentProfil> getDocumentProfils() {
        return documentProfils;
    }

    public ShareProfil documentProfils(Set<DocumentProfil> documentProfils) {
        this.documentProfils = documentProfils;
        return this;
    }

    public ShareProfil addDocumentProfils(DocumentProfil documentProfil) {
        this.documentProfils.add(documentProfil);
        documentProfil.setShareProfil(this);
        return this;
    }

    public ShareProfil removeDocumentProfils(DocumentProfil documentProfil) {
        this.documentProfils.remove(documentProfil);
        documentProfil.setShareProfil(null);
        return this;
    }

    public void setDocumentProfils(Set<DocumentProfil> documentProfils) {
        this.documentProfils = documentProfils;
    }

    public Company getCompany() {
        return company;
    }

    public ShareProfil company(Company company) {
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
        ShareProfil shareProfil = (ShareProfil) o;
        if (shareProfil.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shareProfil.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShareProfil{" +
            "id=" + getId() +
            ", targetJob='" + getTargetJob() + "'" +
            ", skillShield='" + getSkillShield() + "'" +
            ", mainActivity='" + getMainActivity() + "'" +
            ", carryActivity='" + getCarryActivity() + "'" +
            ", skillExpends='" + getSkillExpends() + "'" +
            ", context='" + getContext() + "'" +
            ", collaboType='" + getCollaboType() + "'" +
            ", consideration='" + getConsideration() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
