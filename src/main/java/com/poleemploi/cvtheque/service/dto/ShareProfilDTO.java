package com.poleemploi.cvtheque.service.dto;

import javax.validation.constraints.*;

import com.poleemploi.cvtheque.domain.DocumentProfil;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ShareProfil entity.
 */
public class ShareProfilDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 256)
    private String targetJob;

    @Size(min = 1, max = 256)
    private String skillShield;

    @Size(min = 1, max = 2048)
    private String mainActivity;

    @Size(min = 1, max = 2048)
    private String carryActivity;

    @Size(min = 1, max = 2048)
    private String skillExpends;

    @Size(min = 1, max = 50)
    private String context;

    @Size(min = 1, max = 256)
    private String collaboType;

    @Size(min = 1, max = 50)
    private String consideration;

    @Size(min = 1, max = 2048)
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

    public String getTargetJob() {
        return targetJob;
    }

    public void setTargetJob(String targetJob) {
        this.targetJob = targetJob;
    }

    public String getSkillShield() {
        return skillShield;
    }

    public void setSkillShield(String skillShield) {
        this.skillShield = skillShield;
    }

    public String getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(String mainActivity) {
        this.mainActivity = mainActivity;
    }

    public String getCarryActivity() {
        return carryActivity;
    }

    public void setCarryActivity(String carryActivity) {
        this.carryActivity = carryActivity;
    }

    public String getSkillExpends() {
        return skillExpends;
    }

    public void setSkillExpends(String skillExpends) {
        this.skillExpends = skillExpends;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getCollaboType() {
        return collaboType;
    }

    public void setCollaboType(String collaboType) {
        this.collaboType = collaboType;
    }

    public String getConsideration() {
        return consideration;
    }

    public void setConsideration(String consideration) {
        this.consideration = consideration;
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

        ShareProfilDTO shareProfilDTO = (ShareProfilDTO) o;
        if(shareProfilDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shareProfilDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShareProfilDTO{" +
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
