package com.adloveyou.ms.game.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.adloveyou.ms.game.domain.enumeration.GameStatus;

/**
 * A AdGame.
 */
@Entity
@Table(name = "ad_game")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "adgame")
public class AdGame implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "created")
    private ZonedDateTime created;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private GameStatus status;

    @Column(name = "player_id")
    private Long playerId;

    @OneToOne
    @JoinColumn(unique = true)
    private AdScore score;

    @OneToMany(mappedBy = "adGame")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AdChoise> choises = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public AdGame created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public GameStatus getStatus() {
        return status;
    }

    public AdGame status(GameStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public AdGame playerId(Long playerId) {
        this.playerId = playerId;
        return this;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public AdScore getScore() {
        return score;
    }

    public AdGame score(AdScore adScore) {
        this.score = adScore;
        return this;
    }

    public void setScore(AdScore adScore) {
        this.score = adScore;
    }

    public Set<AdChoise> getChoises() {
        return choises;
    }

    public AdGame choises(Set<AdChoise> adChoises) {
        this.choises = adChoises;
        return this;
    }

    public AdGame addChoises(AdChoise adChoise) {
        this.choises.add(adChoise);
        adChoise.setAdGame(this);
        return this;
    }

    public AdGame removeChoises(AdChoise adChoise) {
        this.choises.remove(adChoise);
        adChoise.setAdGame(null);
        return this;
    }

    public void setChoises(Set<AdChoise> adChoises) {
        this.choises = adChoises;
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
        AdGame adGame = (AdGame) o;
        if (adGame.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adGame.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdGame{" +
            "id=" + getId() +
            ", created='" + getCreated() + "'" +
            ", status='" + getStatus() + "'" +
            ", playerId=" + getPlayerId() +
            "}";
    }
}
