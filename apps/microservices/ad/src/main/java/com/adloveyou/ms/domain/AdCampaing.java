package com.adloveyou.ms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AdCampaing.
 */
@Entity
@Table(name = "ad_campaing")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "adcampaing")
public class AdCampaing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 60)
    @Column(name = "name", length = 60, nullable = false)
    private String name;

    @NotNull
    @Column(name = "initial_amount", precision=10, scale=2, nullable = false)
    private BigDecimal initialAmount;

    @NotNull
    @Column(name = "jhi_start", nullable = false)
    private ZonedDateTime start;

    @NotNull
    @Column(name = "jhi_end", nullable = false)
    private ZonedDateTime end;

    @OneToMany(mappedBy = "adCampaing")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ad> ads = new HashSet<>();

    @OneToMany(mappedBy = "adCampaing")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Product> products = new HashSet<>();

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private Sector sector;

    @ManyToOne
    private Agency providedBy;

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

    public AdCampaing name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getInitialAmount() {
        return initialAmount;
    }

    public AdCampaing initialAmount(BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
        return this;
    }

    public void setInitialAmount(BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public AdCampaing start(ZonedDateTime start) {
        this.start = start;
        return this;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public AdCampaing end(ZonedDateTime end) {
        this.end = end;
        return this;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public Set<Ad> getAds() {
        return ads;
    }

    public AdCampaing ads(Set<Ad> ads) {
        this.ads = ads;
        return this;
    }

    public AdCampaing addAds(Ad ad) {
        this.ads.add(ad);
        ad.setAdCampaing(this);
        return this;
    }

    public AdCampaing removeAds(Ad ad) {
        this.ads.remove(ad);
        ad.setAdCampaing(null);
        return this;
    }

    public void setAds(Set<Ad> ads) {
        this.ads = ads;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public AdCampaing products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public AdCampaing addProducts(Product product) {
        this.products.add(product);
        product.setAdCampaing(this);
        return this;
    }

    public AdCampaing removeProducts(Product product) {
        this.products.remove(product);
        product.setAdCampaing(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Brand getBrand() {
        return brand;
    }

    public AdCampaing brand(Brand brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Sector getSector() {
        return sector;
    }

    public AdCampaing sector(Sector sector) {
        this.sector = sector;
        return this;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Agency getProvidedBy() {
        return providedBy;
    }

    public AdCampaing providedBy(Agency agency) {
        this.providedBy = agency;
        return this;
    }

    public void setProvidedBy(Agency agency) {
        this.providedBy = agency;
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
        AdCampaing adCampaing = (AdCampaing) o;
        if (adCampaing.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adCampaing.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdCampaing{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", initialAmount=" + getInitialAmount() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            "}";
    }
}
