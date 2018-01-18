package com.adloveyou.ms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.adloveyou.ms.domain.enumeration.AdMediaType;

/**
 * A BidCategoryMedia.
 */
@Entity
@Table(name = "bid_category_media")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bidcategorymedia")
public class BidCategoryMedia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "bid", precision=10, scale=2)
    private BigDecimal bid;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type")
    private AdMediaType mediaType;

    @ManyToOne
    private Ad ad;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Media media;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public BidCategoryMedia bid(BigDecimal bid) {
        this.bid = bid;
        return this;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public AdMediaType getMediaType() {
        return mediaType;
    }

    public BidCategoryMedia mediaType(AdMediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public void setMediaType(AdMediaType mediaType) {
        this.mediaType = mediaType;
    }

    public Ad getAd() {
        return ad;
    }

    public BidCategoryMedia ad(Ad ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public Category getCategory() {
        return category;
    }

    public BidCategoryMedia category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Media getMedia() {
        return media;
    }

    public BidCategoryMedia media(Media media) {
        this.media = media;
        return this;
    }

    public void setMedia(Media media) {
        this.media = media;
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
        BidCategoryMedia bidCategoryMedia = (BidCategoryMedia) o;
        if (bidCategoryMedia.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bidCategoryMedia.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BidCategoryMedia{" +
            "id=" + getId() +
            ", bid=" + getBid() +
            ", mediaType='" + getMediaType() + "'" +
            "}";
    }
}
