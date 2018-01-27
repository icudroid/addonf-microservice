package com.adloveyou.ms.game.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Possibility.
 */
@Entity
@Table(name = "possibility")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "possibility")
public class Possibility implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ad_id")
    private Long adId;

    @ManyToOne
    private AdChoise correct;

    @ManyToOne
    private AdChoise possible;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdId() {
        return adId;
    }

    public Possibility adId(Long adId) {
        this.adId = adId;
        return this;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public AdChoise getCorrect() {
        return correct;
    }

    public Possibility correct(AdChoise adChoise) {
        this.correct = adChoise;
        return this;
    }

    public void setCorrect(AdChoise adChoise) {
        this.correct = adChoise;
    }

    public AdChoise getPossible() {
        return possible;
    }

    public Possibility possible(AdChoise adChoise) {
        this.possible = adChoise;
        return this;
    }

    public void setPossible(AdChoise adChoise) {
        this.possible = adChoise;
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
        Possibility possibility = (Possibility) o;
        if (possibility.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), possibility.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Possibility{" +
            "id=" + getId() +
            ", adId=" + getAdId() +
            "}";
    }
}
