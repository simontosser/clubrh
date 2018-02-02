package com.poleemploi.cvtheque.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Document(indexName = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(min = 1, max = 50)
    @Column(name = "jhi_at", length = 50)
    private String at;

    @Size(min = 1, max = 100)
    @Column(name = "dispatch", length = 100)
    private String dispatch;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "address", length = 100, nullable = false)
    private String address;

    @Size(min = 1, max = 100)
    @Column(name = "additional_address", length = 100)
    private String additionalAddress;

    @NotNull
    @Pattern(regexp = "^[0-9]{5}$")
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "city", length = 50, nullable = false)
    private String city;

    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAt() {
        return at;
    }

    public Address at(String at) {
        this.at = at;
        return this;
    }

    public void setAt(String at) {
        this.at = at;
    }

    public String getDispatch() {
        return dispatch;
    }

    public Address dispatch(String dispatch) {
        this.dispatch = dispatch;
        return this;
    }

    public void setDispatch(String dispatch) {
        this.dispatch = dispatch;
    }

    public String getAddress() {
        return address;
    }

    public Address address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdditionalAddress() {
        return additionalAddress;
    }

    public Address additionalAddress(String additionalAddress) {
        this.additionalAddress = additionalAddress;
        return this;
    }

    public void setAdditionalAddress(String additionalAddress) {
        this.additionalAddress = additionalAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Address zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public Address city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Company getCompany() {
        return company;
    }

    public Address company(Company company) {
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
        Address address = (Address) o;
        if (address.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), address.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", at='" + getAt() + "'" +
            ", dispatch='" + getDispatch() + "'" +
            ", address='" + getAddress() + "'" +
            ", additionalAddress='" + getAdditionalAddress() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", city='" + getCity() + "'" +
            "}";
    }
}
