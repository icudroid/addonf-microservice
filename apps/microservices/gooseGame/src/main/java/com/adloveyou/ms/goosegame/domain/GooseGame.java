package com.adloveyou.ms.goosegame.domain;

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

import com.adloveyou.ms.goosegame.domain.enumeration.GooseGameStatus;

/**
 * A GooseGame.
 */
@Entity
@Table(name = "goose_game")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "goosegame")
public class GooseGame implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "created")
    private ZonedDateTime created;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private GooseGameStatus status;

    @OneToMany(mappedBy = "gooseGame")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GooseLevel> levels = new HashSet<>();

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

    public GooseGame name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public GooseGame created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public GooseGameStatus getStatus() {
        return status;
    }

    public GooseGame status(GooseGameStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(GooseGameStatus status) {
        this.status = status;
    }

    public Set<GooseLevel> getLevels() {
        return levels;
    }

    public GooseGame levels(Set<GooseLevel> gooseLevels) {
        this.levels = gooseLevels;
        return this;
    }

    public GooseGame addLevels(GooseLevel gooseLevel) {
        this.levels.add(gooseLevel);
        gooseLevel.setGooseGame(this);
        return this;
    }

    public GooseGame removeLevels(GooseLevel gooseLevel) {
        this.levels.remove(gooseLevel);
        gooseLevel.setGooseGame(null);
        return this;
    }

    public void setLevels(Set<GooseLevel> gooseLevels) {
        this.levels = gooseLevels;
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
        GooseGame gooseGame = (GooseGame) o;
        if (gooseGame.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gooseGame.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GooseGame{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", created='" + getCreated() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
