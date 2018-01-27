package com.adloveyou.ms.ad.domain;

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

import com.adloveyou.ms.ad.domain.enumeration.AdStatus;

/**
 * A Ad.
 */
@Entity
@Table(name = "ad")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ad")
public class Ad implements Serializable {

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

    @NotNull
    @Column(name = "duration", nullable = false)
    private Long duration;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AdStatus status;

    @NotNull
    @Column(name = "adfile_id", nullable = false)
    private Long adfileId;

    @OneToMany(mappedBy = "ad")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "ad")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AdConfiguration> configs = new HashSet<>();

    @OneToMany(mappedBy = "ad")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AdRestriction> restrictions = new HashSet<>();

    @OneToMany(mappedBy = "ad")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BidCategoryMedia> bids = new HashSet<>();

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

    public Ad name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getInitialAmount() {
        return initialAmount;
    }

    public Ad initialAmount(BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
        return this;
    }

    public void setInitialAmount(BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public Ad start(ZonedDateTime start) {
        this.start = start;
        return this;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public Ad end(ZonedDateTime end) {
        this.end = end;
        return this;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public Long getDuration() {
        return duration;
    }

    public Ad duration(Long duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public AdStatus getStatus() {
        return status;
    }

    public Ad status(AdStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(AdStatus status) {
        this.status = status;
    }

    public Long getAdfileId() {
        return adfileId;
    }

    public Ad adfileId(Long adfileId) {
        this.adfileId = adfileId;
        return this;
    }

    public void setAdfileId(Long adfileId) {
        this.adfileId = adfileId;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Ad products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Ad addProducts(Product product) {
        this.products.add(product);
        product.setAd(this);
        return this;
    }

    public Ad removeProducts(Product product) {
        this.products.remove(product);
        product.setAd(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<AdConfiguration> getConfigs() {
        return configs;
    }

    public Ad configs(Set<AdConfiguration> adConfigurations) {
        this.configs = adConfigurations;
        return this;
    }

    public Ad addConfigs(AdConfiguration adConfiguration) {
        this.configs.add(adConfiguration);
        adConfiguration.setAd(this);
        return this;
    }

    public Ad removeConfigs(AdConfiguration adConfiguration) {
        this.configs.remove(adConfiguration);
        adConfiguration.setAd(null);
        return this;
    }

    public void setConfigs(Set<AdConfiguration> adConfigurations) {
        this.configs = adConfigurations;
    }

    public Set<AdRestriction> getRestrictions() {
        return restrictions;
    }

    public Ad restrictions(Set<AdRestriction> adRestrictions) {
        this.restrictions = adRestrictions;
        return this;
    }

    public Ad addRestrictions(AdRestriction adRestriction) {
        this.restrictions.add(adRestriction);
        adRestriction.setAd(this);
        return this;
    }

    public Ad removeRestrictions(AdRestriction adRestriction) {
        this.restrictions.remove(adRestriction);
        adRestriction.setAd(null);
        return this;
    }

    public void setRestrictions(Set<AdRestriction> adRestrictions) {
        this.restrictions = adRestrictions;
    }

    public Set<BidCategoryMedia> getBids() {
        return bids;
    }

    public Ad bids(Set<BidCategoryMedia> bidCategoryMedias) {
        this.bids = bidCategoryMedias;
        return this;
    }

    public Ad addBids(BidCategoryMedia bidCategoryMedia) {
        this.bids.add(bidCategoryMedia);
        bidCategoryMedia.setAd(this);
        return this;
    }

    public Ad removeBids(BidCategoryMedia bidCategoryMedia) {
        this.bids.remove(bidCategoryMedia);
        bidCategoryMedia.setAd(null);
        return this;
    }

    public void setBids(Set<BidCategoryMedia> bidCategoryMedias) {
        this.bids = bidCategoryMedias;
    }

    public Brand getBrand() {
        return brand;
    }

    public Ad brand(Brand brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Sector getSector() {
        return sector;
    }

    public Ad sector(Sector sector) {
        this.sector = sector;
        return this;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Agency getProvidedBy() {
        return providedBy;
    }

    public Ad providedBy(Agency agency) {
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
        Ad ad = (Ad) o;
        if (ad.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ad.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ad{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", initialAmount=" + getInitialAmount() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", duration=" + getDuration() +
            ", status='" + getStatus() + "'" +
            ", adfileId=" + getAdfileId() +
            "}";
    }
}
