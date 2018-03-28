package com.poleemploi.cvtheque.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;

import com.poleemploi.cvtheque.domain.DocumentProfil;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the RecrutementProfil entity.
 */
public class RecrutementProfilDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 256)
    private String jobTitle;

    @Size(min = 0, max = 50)
    private String context;

    @Size(min = 0, max = 256)
    private String finalyObject;

    @Size(min = 0, max = 2048)
    private String mainActivity;

    @Size(min = 0, max = 2048)
    private String mainSkill;

    @Size(min = 0, max = 2048)
    private String knownledge;

    private LocalDate deadline;

    @Size(min = 0, max = 50)
    private String consideration;

    @Size(min = 0, max = 50)
    private String remind;

    @Size(min = 0, max = 2048)
    private String comment;

    private Long companyId;

    private String companyName;
    
    private Set<DocumentProfilDTO> documentProfils = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getFinalyObject() {
        return finalyObject;
    }

    public void setFinalyObject(String finalyObject) {
        this.finalyObject = finalyObject;
    }

    public String getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(String mainActivity) {
        this.mainActivity = mainActivity;
    }

    public String getMainSkill() {
        return mainSkill;
    }

    public void setMainSkill(String mainSkill) {
        this.mainSkill = mainSkill;
    }

    public String getKnownledge() {
        return knownledge;
    }

    public void setKnownledge(String knownledge) {
        this.knownledge = knownledge;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getConsideration() {
        return consideration;
    }

    public void setConsideration(String consideration) {
        this.consideration = consideration;
    }

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public Set<DocumentProfilDTO> getDocumentProfils() {
		return documentProfils;
	}

	public void setDocumentProfils(Set<DocumentProfilDTO> documentProfils) {
		this.documentProfils = documentProfils;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecrutementProfilDTO recrutementProfilDTO = (RecrutementProfilDTO) o;
        if(recrutementProfilDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recrutementProfilDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecrutementProfilDTO{" +
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
