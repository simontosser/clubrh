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
 * A Company.
 */
@Entity
@Table(name = "company")
@Document(indexName = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;


    @Size(min = 1, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Size(min = 1, max = 100)
    @Column(name = "activity", length = 100)
    private String activity;

    @Pattern(regexp = "^(0|\\(?\\+33\\)?\\s?|0033\\s?)[1-79]([\\.\\-\\s]?\\d\\d){4}$")
    @Column(name = "phone")
    private String phone;

    @Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
    @Column(name = "email")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private Set<ShareProfil> shareProfils = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private Set<RecrutementProfil> recrutementProfils = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivity() {
        return activity;
    }

    public Company activity(String activity) {
        this.activity = activity;
        return this;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getPhone() {
        return phone;
    }

    public Company phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public Company email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public Company address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<ShareProfil> getShareProfils() {
        return shareProfils;
    }

    public Company shareProfils(Set<ShareProfil> shareProfils) {
        this.shareProfils = shareProfils;
        return this;
    }

    public Company addShareProfils(ShareProfil shareProfil) {
        this.shareProfils.add(shareProfil);
        shareProfil.setCompany(this);
        return this;
    }

    public Company removeShareProfils(ShareProfil shareProfil) {
        this.shareProfils.remove(shareProfil);
        shareProfil.setCompany(null);
        return this;
    }

    public void setShareProfils(Set<ShareProfil> shareProfils) {
        this.shareProfils = shareProfils;
    }

    public Set<RecrutementProfil> getRecrutementProfils() {
        return recrutementProfils;
    }

    public Company recrutementProfils(Set<RecrutementProfil> recrutementProfils) {
        this.recrutementProfils = recrutementProfils;
        return this;
    }

    public Company addRecrutementProfils(RecrutementProfil recrutementProfil) {
        this.recrutementProfils.add(recrutementProfil);
        recrutementProfil.setCompany(this);
        return this;
    }

    public Company removeRecrutementProfils(RecrutementProfil recrutementProfil) {
        this.recrutementProfils.remove(recrutementProfil);
        recrutementProfil.setCompany(null);
        return this;
    }

    public void setRecrutementProfils(Set<RecrutementProfil> recrutementProfils) {
        this.recrutementProfils = recrutementProfils;
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
        Company company = (Company) o;
        if (company.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), company.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", activity='" + getActivity() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
