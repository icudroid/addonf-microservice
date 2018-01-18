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
 * A Brand.
 */
@Entity
@Table(name = "brand")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "brand")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "user_id")
    private Long userId;

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

    @OneToMany(mappedBy = "brand")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "brand")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CustomerTarget> targets = new HashSet<>();

    @OneToMany(mappedBy = "brand")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MediaTarget> targetsMedias = new HashSet<>();

    @OneToMany(mappedBy = "brand")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BrandUser> users = new HashSet<>();

    @OneToMany(mappedBy = "brand")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contact> contacts = new HashSet<>();

    @OneToMany(mappedBy = "brand")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FileAttachement> attachements = new HashSet<>();

    @ManyToOne
    private Sector sector;

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

    public Brand name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public Brand userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSiret() {
        return siret;
    }

    public Brand siret(String siret) {
        this.siret = siret;
        return this;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public String getSiren() {
        return siren;
    }

    public Brand siren(String siren) {
        this.siren = siren;
        return this;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public LegalStatus getLegalStatus() {
        return legalStatus;
    }

    public Brand legalStatus(LegalStatus legalStatus) {
        this.legalStatus = legalStatus;
        return this;
    }

    public void setLegalStatus(LegalStatus legalStatus) {
        this.legalStatus = legalStatus;
    }

    public byte[] getLogo() {
        return logo;
    }

    public Brand logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public Brand logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Brand products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Brand addProducts(Product product) {
        this.products.add(product);
        product.setBrand(this);
        return this;
    }

    public Brand removeProducts(Product product) {
        this.products.remove(product);
        product.setBrand(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<CustomerTarget> getTargets() {
        return targets;
    }

    public Brand targets(Set<CustomerTarget> customerTargets) {
        this.targets = customerTargets;
        return this;
    }

    public Brand addTargets(CustomerTarget customerTarget) {
        this.targets.add(customerTarget);
        customerTarget.setBrand(this);
        return this;
    }

    public Brand removeTargets(CustomerTarget customerTarget) {
        this.targets.remove(customerTarget);
        customerTarget.setBrand(null);
        return this;
    }

    public void setTargets(Set<CustomerTarget> customerTargets) {
        this.targets = customerTargets;
    }

    public Set<MediaTarget> getTargetsMedias() {
        return targetsMedias;
    }

    public Brand targetsMedias(Set<MediaTarget> mediaTargets) {
        this.targetsMedias = mediaTargets;
        return this;
    }

    public Brand addTargetsMedia(MediaTarget mediaTarget) {
        this.targetsMedias.add(mediaTarget);
        mediaTarget.setBrand(this);
        return this;
    }

    public Brand removeTargetsMedia(MediaTarget mediaTarget) {
        this.targetsMedias.remove(mediaTarget);
        mediaTarget.setBrand(null);
        return this;
    }

    public void setTargetsMedias(Set<MediaTarget> mediaTargets) {
        this.targetsMedias = mediaTargets;
    }

    public Set<BrandUser> getUsers() {
        return users;
    }

    public Brand users(Set<BrandUser> brandUsers) {
        this.users = brandUsers;
        return this;
    }

    public Brand addUsers(BrandUser brandUser) {
        this.users.add(brandUser);
        brandUser.setBrand(this);
        return this;
    }

    public Brand removeUsers(BrandUser brandUser) {
        this.users.remove(brandUser);
        brandUser.setBrand(null);
        return this;
    }

    public void setUsers(Set<BrandUser> brandUsers) {
        this.users = brandUsers;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public Brand contacts(Set<Contact> contacts) {
        this.contacts = contacts;
        return this;
    }

    public Brand addContacts(Contact contact) {
        this.contacts.add(contact);
        contact.setBrand(this);
        return this;
    }

    public Brand removeContacts(Contact contact) {
        this.contacts.remove(contact);
        contact.setBrand(null);
        return this;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Set<FileAttachement> getAttachements() {
        return attachements;
    }

    public Brand attachements(Set<FileAttachement> fileAttachements) {
        this.attachements = fileAttachements;
        return this;
    }

    public Brand addAttachements(FileAttachement fileAttachement) {
        this.attachements.add(fileAttachement);
        fileAttachement.setBrand(this);
        return this;
    }

    public Brand removeAttachements(FileAttachement fileAttachement) {
        this.attachements.remove(fileAttachement);
        fileAttachement.setBrand(null);
        return this;
    }

    public void setAttachements(Set<FileAttachement> fileAttachements) {
        this.attachements = fileAttachements;
    }

    public Sector getSector() {
        return sector;
    }

    public Brand sector(Sector sector) {
        this.sector = sector;
        return this;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
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
        Brand brand = (Brand) o;
        if (brand.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), brand.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Brand{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", userId=" + getUserId() +
            ", siret='" + getSiret() + "'" +
            ", siren='" + getSiren() + "'" +
            ", legalStatus='" + getLegalStatus() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            "}";
    }
}
