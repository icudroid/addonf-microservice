package com.adloveyou.ms.goosegame.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A GooseToken.
 */
@Entity
@Table(name = "goose_token")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "goosetoken")
public class GooseToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "player_id")
    private Long playerId;

    @ManyToOne
    private GooseCase where;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public GooseToken playerId(Long playerId) {
        this.playerId = playerId;
        return this;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public GooseCase getWhere() {
        return where;
    }

    public GooseToken where(GooseCase gooseCase) {
        this.where = gooseCase;
        return this;
    }

    public void setWhere(GooseCase gooseCase) {
        this.where = gooseCase;
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
        GooseToken gooseToken = (GooseToken) o;
        if (gooseToken.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gooseToken.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GooseToken{" +
            "id=" + getId() +
            ", playerId=" + getPlayerId() +
            "}";
    }
}
