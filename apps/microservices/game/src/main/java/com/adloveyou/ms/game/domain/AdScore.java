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
 * A AdScore.
 */
@Entity
@Table(name = "ad_score")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "adscore")
public class AdScore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "score")
    private Integer score;

    @OneToMany(mappedBy = "adScore")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AdPlayerResponse> answers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public AdScore score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Set<AdPlayerResponse> getAnswers() {
        return answers;
    }

    public AdScore answers(Set<AdPlayerResponse> adPlayerResponses) {
        this.answers = adPlayerResponses;
        return this;
    }

    public AdScore addAnswers(AdPlayerResponse adPlayerResponse) {
        this.answers.add(adPlayerResponse);
        adPlayerResponse.setAdScore(this);
        return this;
    }

    public AdScore removeAnswers(AdPlayerResponse adPlayerResponse) {
        this.answers.remove(adPlayerResponse);
        adPlayerResponse.setAdScore(null);
        return this;
    }

    public void setAnswers(Set<AdPlayerResponse> adPlayerResponses) {
        this.answers = adPlayerResponses;
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
        AdScore adScore = (AdScore) o;
        if (adScore.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adScore.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdScore{" +
            "id=" + getId() +
            ", score=" + getScore() +
            "}";
    }
}
