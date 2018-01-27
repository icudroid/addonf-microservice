package com.adloveyou.ms.goosegame.domain;

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
 * A GooseLevel.
 */
@Entity
@Table(name = "goose_level")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "gooselevel")
public class GooseLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_level")
    private Integer level;

    @Column(name = "limited_time")
    private Long limitedTime;

    @Column(name = "nb_max_ad_by_play")
    private Integer nbMaxAdByPlay;

    @ManyToOne
    private GooseGame gooseGame;

    @OneToOne
    @JoinColumn(unique = true)
    private GooseCase start;

    @OneToOne
    @JoinColumn(unique = true)
    private GooseCase end;

    @OneToMany(mappedBy = "gooseLevel")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GooseCase> cases = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public GooseLevel level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getLimitedTime() {
        return limitedTime;
    }

    public GooseLevel limitedTime(Long limitedTime) {
        this.limitedTime = limitedTime;
        return this;
    }

    public void setLimitedTime(Long limitedTime) {
        this.limitedTime = limitedTime;
    }

    public Integer getNbMaxAdByPlay() {
        return nbMaxAdByPlay;
    }

    public GooseLevel nbMaxAdByPlay(Integer nbMaxAdByPlay) {
        this.nbMaxAdByPlay = nbMaxAdByPlay;
        return this;
    }

    public void setNbMaxAdByPlay(Integer nbMaxAdByPlay) {
        this.nbMaxAdByPlay = nbMaxAdByPlay;
    }

    public GooseGame getGooseGame() {
        return gooseGame;
    }

    public GooseLevel gooseGame(GooseGame gooseGame) {
        this.gooseGame = gooseGame;
        return this;
    }

    public void setGooseGame(GooseGame gooseGame) {
        this.gooseGame = gooseGame;
    }

    public GooseCase getStart() {
        return start;
    }

    public GooseLevel start(GooseCase gooseCase) {
        this.start = gooseCase;
        return this;
    }

    public void setStart(GooseCase gooseCase) {
        this.start = gooseCase;
    }

    public GooseCase getEnd() {
        return end;
    }

    public GooseLevel end(GooseCase gooseCase) {
        this.end = gooseCase;
        return this;
    }

    public void setEnd(GooseCase gooseCase) {
        this.end = gooseCase;
    }

    public Set<GooseCase> getCases() {
        return cases;
    }

    public GooseLevel cases(Set<GooseCase> gooseCases) {
        this.cases = gooseCases;
        return this;
    }

    public GooseLevel addCases(GooseCase gooseCase) {
        this.cases.add(gooseCase);
        gooseCase.setGooseLevel(this);
        return this;
    }

    public GooseLevel removeCases(GooseCase gooseCase) {
        this.cases.remove(gooseCase);
        gooseCase.setGooseLevel(null);
        return this;
    }

    public void setCases(Set<GooseCase> gooseCases) {
        this.cases = gooseCases;
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
        GooseLevel gooseLevel = (GooseLevel) o;
        if (gooseLevel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gooseLevel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GooseLevel{" +
            "id=" + getId() +
            ", level=" + getLevel() +
            ", limitedTime=" + getLimitedTime() +
            ", nbMaxAdByPlay=" + getNbMaxAdByPlay() +
            "}";
    }
}
