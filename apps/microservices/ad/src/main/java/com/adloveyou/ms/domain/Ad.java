package com.adloveyou.ms.domain;

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

import com.adloveyou.ms.domain.enumeration.AdStatus;

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
    @Column(name = "duration", nullable = false)
    private Long duration;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AdStatus status;

    @NotNull
    @Column(name = "adfile_id", nullable = false)
    private Long adfileId;

    @ManyToOne
    private AdCampaing adCampaing;

    @OneToMany(mappedBy = "ad")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BidCategoryMedia> bids = new HashSet<>();

    @OneToMany(mappedBy = "ad")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AdRule> rules = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public AdCampaing getAdCampaing() {
        return adCampaing;
    }

    public Ad adCampaing(AdCampaing adCampaing) {
        this.adCampaing = adCampaing;
        return this;
    }

    public void setAdCampaing(AdCampaing adCampaing) {
        this.adCampaing = adCampaing;
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

    public Set<AdRule> getRules() {
        return rules;
    }

    public Ad rules(Set<AdRule> adRules) {
        this.rules = adRules;
        return this;
    }

    public Ad addRules(AdRule adRule) {
        this.rules.add(adRule);
        adRule.setAd(this);
        return this;
    }

    public Ad removeRules(AdRule adRule) {
        this.rules.remove(adRule);
        adRule.setAd(null);
        return this;
    }

    public void setRules(Set<AdRule> adRules) {
        this.rules = adRules;
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
            ", duration=" + getDuration() +
            ", status='" + getStatus() + "'" +
            ", adfileId=" + getAdfileId() +
            "}";
    }
}
