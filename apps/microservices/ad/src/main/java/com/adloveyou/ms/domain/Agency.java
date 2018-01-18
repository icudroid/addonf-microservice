package com.adloveyou.ms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.adloveyou.ms.domain.enumeration.LegalStatus;

/**
 * A Agency.
 */
@Entity
@Table(name = "agency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "agency")
public class Agency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "siret")
    private String siret;

    @Column(name = "siren")
    private String siren;

    @Enumerated(EnumType.STRING)
    @Column(name = "legal_status")
    private LegalStatus legalStatus;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @OneToMany(mappedBy = "agency")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AgencyUser> users = new HashSet<>();

    @OneToMany(mappedBy = "agency")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contact> contacts = new HashSet<>();

    @OneToMany(mappedBy = "agency")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FileAttachement> attachements = new HashSet<>();

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

    public Agency name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSiret() {
        return siret;
    }

    public Agency siret(String siret) {
        this.siret = siret;
        return this;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public String getSiren() {
        return siren;
    }

    public Agency siren(String siren) {
        this.siren = siren;
        return this;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public LegalStatus getLegalStatus() {
        return legalStatus;
    }

    public Agency legalStatus(LegalStatus legalStatus) {
        this.legalStatus = legalStatus;
        return this;
    }

    public void setLegalStatus(LegalStatus legalStatus) {
        this.legalStatus = legalStatus;
    }

    public byte[] getLogo() {
        return logo;
    }

    public Agency logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public Agency logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public Set<AgencyUser> getUsers() {
        return users;
    }

    public Agency users(Set<AgencyUser> agencyUsers) {
        this.users = agencyUsers;
        return this;
    }

    public Agency addUsers(AgencyUser agencyUser) {
        this.users.add(agencyUser);
        agencyUser.setAgency(this);
        return this;
    }

    public Agency removeUsers(AgencyUser agencyUser) {
        this.users.remove(agencyUser);
        agencyUser.setAgency(null);
        return this;
    }

    public void setUsers(Set<AgencyUser> agencyUsers) {
        this.users = agencyUsers;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public Agency contacts(Set<Contact> contacts) {
        this.contacts = contacts;
        return this;
    }

    public Agency addContacts(Contact contact) {
        this.contacts.add(contact);
        contact.setAgency(this);
        return this;
    }

    public Agency removeContacts(Contact contact) {
        this.contacts.remove(contact);
        contact.setAgency(null);
        return this;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Set<FileAttachement> getAttachements() {
        return attachements;
    }

    public Agency attachements(Set<FileAttachement> fileAttachements) {
        this.attachements = fileAttachements;
        return this;
    }

    public Agency addAttachements(FileAttachement fileAttachement) {
        this.attachements.add(fileAttachement);
        fileAttachement.setAgency(this);
        return this;
    }

    public Agency removeAttachements(FileAttachement fileAttachement) {
        this.attachements.remove(fileAttachement);
        fileAttachement.setAgency(null);
        return this;
    }

    public void setAttachements(Set<FileAttachement> fileAttachements) {
        this.attachements = fileAttachements;
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
        Agency agency = (Agency) o;
        if (agency.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agency.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Agency{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", siret='" + getSiret() + "'" +
            ", siren='" + getSiren() + "'" +
            ", legalStatus='" + getLegalStatus() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            "}";
    }
}
