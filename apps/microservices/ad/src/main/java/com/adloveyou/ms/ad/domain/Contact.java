package com.adloveyou.ms.ad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Contact.
 */
@Entity
@Table(name = "contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "contact")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 60)
    @Column(name = "lastname", length = 60, nullable = false)
    private String lastname;

    @NotNull
    @Size(max = 60)
    @Column(name = "firstname", length = 60, nullable = false)
    private String firstname;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private Agency agency;

    @ManyToOne
    private Media media;

    @OneToMany(mappedBy = "contact")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MeanOfContact> meanOfContacts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public Contact lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public Contact firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Brand getBrand() {
        return brand;
    }

    public Contact brand(Brand brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Agency getAgency() {
        return agency;
    }

    public Contact agency(Agency agency) {
        this.agency = agency;
        return this;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public Media getMedia() {
        return media;
    }

    public Contact media(Media media) {
        this.media = media;
        return this;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Set<MeanOfContact> getMeanOfContacts() {
        return meanOfContacts;
    }

    public Contact meanOfContacts(Set<MeanOfContact> meanOfContacts) {
        this.meanOfContacts = meanOfContacts;
        return this;
    }

    public Contact addMeanOfContacts(MeanOfContact meanOfContact) {
        this.meanOfContacts.add(meanOfContact);
        meanOfContact.setContact(this);
        return this;
    }

    public Contact removeMeanOfContacts(MeanOfContact meanOfContact) {
        this.meanOfContacts.remove(meanOfContact);
        meanOfContact.setContact(null);
        return this;
    }

    public void setMeanOfContacts(Set<MeanOfContact> meanOfContacts) {
        this.meanOfContacts = meanOfContacts;
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
        Contact contact = (Contact) o;
        if (contact.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", lastname='" + getLastname() + "'" +
            ", firstname='" + getFirstname() + "'" +
            "}";
    }
}
