package com.adloveyou.ms.game.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.adloveyou.ms.game.domain.enumeration.GameStatus;

/**
 * A DTO for the AdGame entity.
 */
public class AdGameDTO implements Serializable {

    private Long id;

    private ZonedDateTime created;

    private GameStatus status;

    private Long playerId;

    private Long scoreId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long adScoreId) {
        this.scoreId = adScoreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdGameDTO adGameDTO = (AdGameDTO) o;
        if(adGameDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adGameDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdGameDTO{" +
            "id=" + getId() +
            ", created='" + getCreated() + "'" +
            ", status='" + getStatus() + "'" +
            ", playerId=" + getPlayerId() +
            "}";
    }
}
