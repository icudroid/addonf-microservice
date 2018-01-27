package com.adloveyou.ms.game.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AdChoise.
 */
@Entity
@Table(name = "ad_choise")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "adchoise")
public class AdChoise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_number")
    private Integer number;

    @Column(name = "question")
    private String question;

    @Column(name = "bid_id")
    private Long bidId;

    @Column(name = "ad_config_id")
    private Long adConfigId;

    @ManyToOne
    private AdGame adGame;

    @OneToMany(mappedBy = "correct")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Possibility> corrects = new HashSet<>();

    @OneToMany(mappedBy = "possible")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Possibility> possiblities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public AdChoise number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getQuestion() {
        return question;
    }

    public AdChoise question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Long getBidId() {
        return bidId;
    }

    public AdChoise bidId(Long bidId) {
        this.bidId = bidId;
        return this;
    }

    public void setBidId(Long bidId) {
        this.bidId = bidId;
    }

    public Long getAdConfigId() {
        return adConfigId;
    }

    public AdChoise adConfigId(Long adConfigId) {
        this.adConfigId = adConfigId;
        return this;
    }

    public void setAdConfigId(Long adConfigId) {
        this.adConfigId = adConfigId;
    }

    public AdGame getAdGame() {
        return adGame;
    }

    public AdChoise adGame(AdGame adGame) {
        this.adGame = adGame;
        return this;
    }

    public void setAdGame(AdGame adGame) {
        this.adGame = adGame;
    }

    public Set<Possibility> getCorrects() {
        return corrects;
    }

    public AdChoise corrects(Set<Possibility> possibilities) {
        this.corrects = possibilities;
        return this;
    }

    public AdChoise addCorrects(Possibility possibility) {
        this.corrects.add(possibility);
        possibility.setCorrect(this);
        return this;
    }

    public AdChoise removeCorrects(Possibility possibility) {
        this.corrects.remove(possibility);
        possibility.setCorrect(null);
        return this;
    }

    public void setCorrects(Set<Possibility> possibilities) {
        this.corrects = possibilities;
    }

    public Set<Possibility> getPossiblities() {
        return possiblities;
    }

    public AdChoise possiblities(Set<Possibility> possibilities) {
        this.possiblities = possibilities;
        return this;
    }

    public AdChoise addPossiblities(Possibility possibility) {
        this.possiblities.add(possibility);
        possibility.setPossible(this);
        return this;
    }

    public AdChoise removePossiblities(Possibility possibility) {
        this.possiblities.remove(possibility);
        possibility.setPossible(null);
        return this;
    }

    public void setPossiblities(Set<Possibility> possibilities) {
        this.possiblities = possibilities;
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
        AdChoise adChoise = (AdChoise) o;
        if (adChoise.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adChoise.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdChoise{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", question='" + getQuestion() + "'" +
            ", bidId=" + getBidId() +
            ", adConfigId=" + getAdConfigId() +
            "}";
    }
}
