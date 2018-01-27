package com.adloveyou.ms.ad.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AdRestriction.
 */
@Entity
@Table(name = "ad_restriction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "adrestriction")
public class AdRestriction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    private Ad ad;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ad getAd() {
        return ad;
    }

    public AdRestriction ad(Ad ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
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
        AdRestriction adRestriction = (AdRestriction) o;
        if (adRestriction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adRestriction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdRestriction{" +
            "id=" + getId() +
            "}";
    }
}
