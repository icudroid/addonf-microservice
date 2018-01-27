package com.adloveyou.ms.goosegame.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.adloveyou.ms.goosegame.domain.enumeration.WinStatus;

/**
 * A GooseWin.
 */
@Entity
@Table(name = "goose_win")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "goosewin")
public class GooseWin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_value")
    private Integer value;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WinStatus status;

    @Column(name = "won")
    private ZonedDateTime won;

    @Column(name = "player_id")
    private Long playerId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public GooseWin value(Integer value) {
        this.value = value;
        return this;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public WinStatus getStatus() {
        return status;
    }

    public GooseWin status(WinStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(WinStatus status) {
        this.status = status;
    }

    public ZonedDateTime getWon() {
        return won;
    }

    public GooseWin won(ZonedDateTime won) {
        this.won = won;
        return this;
    }

    public void setWon(ZonedDateTime won) {
        this.won = won;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public GooseWin playerId(Long playerId) {
        this.playerId = playerId;
        return this;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
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
        GooseWin gooseWin = (GooseWin) o;
        if (gooseWin.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gooseWin.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GooseWin{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", status='" + getStatus() + "'" +
            ", won='" + getWon() + "'" +
            ", playerId=" + getPlayerId() +
            "}";
    }
}
