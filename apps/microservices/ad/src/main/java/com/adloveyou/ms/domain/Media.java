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
 * A Media.
 */
@Entity
@Table(name = "media")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "media")
public class Media implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "pass_phrase")
    private String passPhrase;

    @Column(name = "ext_id")
    private String extId;

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

    @OneToMany(mappedBy = "media")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MediaUser> users = new HashSet<>();

    @OneToMany(mappedBy = "media")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contact> contacts = new HashSet<>();

    @OneToMany(mappedBy = "media")
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

    public Media name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassPhrase() {
        return passPhrase;
    }

    public Media passPhrase(String passPhrase) {
        this.passPhrase = passPhrase;
        return this;
    }

    public void setPassPhrase(String passPhrase) {
        this.passPhrase = passPhrase;
    }

    public String getExtId() {
        return extId;
    }

    public Media extId(String extId) {
        this.extId = extId;
        return this;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public String getSiret() {
        return siret;
    }

    public Media siret(String siret) {
        this.siret = siret;
        return this;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public String getSiren() {
        return siren;
    }

    public Media siren(String siren) {
        this.siren = siren;
        return this;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public LegalStatus getLegalStatus() {
        return legalStatus;
    }

    public Media legalStatus(LegalStatus legalStatus) {
        this.legalStatus = legalStatus;
        return this;
    }

    public void setLegalStatus(LegalStatus legalStatus) {
        this.legalStatus = legalStatus;
    }

    public byte[] getLogo() {
        return logo;
    }

    public Media logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public Media logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public Set<MediaUser> getUsers() {
        return users;
    }

    public Media users(Set<MediaUser> mediaUsers) {
        this.users = mediaUsers;
        return this;
    }

    public Media addUsers(MediaUser mediaUser) {
        this.users.add(mediaUser);
        mediaUser.setMedia(this);
        return this;
    }

    public Media removeUsers(MediaUser mediaUser) {
        this.users.remove(mediaUser);
        mediaUser.setMedia(null);
        return this;
    }

    public void setUsers(Set<MediaUser> mediaUsers) {
        this.users = mediaUsers;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public Media contacts(Set<Contact> contacts) {
        this.contacts = contacts;
        return this;
    }

    public Media addContacts(Contact contact) {
        this.contacts.add(contact);
        contact.setMedia(this);
        return this;
    }

    public Media removeContacts(Contact contact) {
        this.contacts.remove(contact);
        contact.setMedia(null);
        return this;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Set<FileAttachement> getAttachements() {
        return attachements;
    }

    public Media attachements(Set<FileAttachement> fileAttachements) {
        this.attachements = fileAttachements;
        return this;
    }

    public Media addAttachements(FileAttachement fileAttachement) {
        this.attachements.add(fileAttachement);
        fileAttachement.setMedia(this);
        return this;
    }

    public Media removeAttachements(FileAttachement fileAttachement) {
        this.attachements.remove(fileAttachement);
        fileAttachement.setMedia(null);
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
        Media media = (Media) o;
        if (media.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), media.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Media{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", passPhrase='" + getPassPhrase() + "'" +
            ", extId='" + getExtId() + "'" +
            ", siret='" + getSiret() + "'" +
            ", siren='" + getSiren() + "'" +
            ", legalStatus='" + getLegalStatus() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            "}";
    }
}
