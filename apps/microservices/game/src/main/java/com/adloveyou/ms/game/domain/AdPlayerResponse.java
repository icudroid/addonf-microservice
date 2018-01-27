package com.adloveyou.ms.game.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AdPlayerResponse.
 */
@Entity
@Table(name = "ad_player_response")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "adplayerresponse")
public class AdPlayerResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_number")
    private Integer number;

    @Column(name = "correct_answer")
    private Boolean correctAnswer;

    @Column(name = "played")
    private Boolean played;

    @ManyToOne
    private AdScore adScore;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "ad_player_response_responses",
               joinColumns = @JoinColumn(name="ad_player_responses_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="responses_id", referencedColumnName="id"))
    private Set<Possibility> responses = new HashSet<>();

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

    public AdPlayerResponse number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public AdPlayerResponse correctAnswer(Boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
        return this;
    }

    public void setCorrectAnswer(Boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Boolean isPlayed() {
        return played;
    }

    public AdPlayerResponse played(Boolean played) {
        this.played = played;
        return this;
    }

    public void setPlayed(Boolean played) {
        this.played = played;
    }

    public AdScore getAdScore() {
        return adScore;
    }

    public AdPlayerResponse adScore(AdScore adScore) {
        this.adScore = adScore;
        return this;
    }

    public void setAdScore(AdScore adScore) {
        this.adScore = adScore;
    }

    public Set<Possibility> getResponses() {
        return responses;
    }

    public AdPlayerResponse responses(Set<Possibility> possibilities) {
        this.responses = possibilities;
        return this;
    }

    public AdPlayerResponse addResponses(Possibility possibility) {
        this.responses.add(possibility);
        return this;
    }

    public AdPlayerResponse removeResponses(Possibility possibility) {
        this.responses.remove(possibility);
        return this;
    }

    public void setResponses(Set<Possibility> possibilities) {
        this.responses = possibilities;
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
        AdPlayerResponse adPlayerResponse = (AdPlayerResponse) o;
        if (adPlayerResponse.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adPlayerResponse.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdPlayerResponse{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", correctAnswer='" + isCorrectAnswer() + "'" +
            ", played='" + isPlayed() + "'" +
            "}";
    }
}
