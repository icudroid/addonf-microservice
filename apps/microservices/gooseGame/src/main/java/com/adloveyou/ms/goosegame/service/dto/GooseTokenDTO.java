package com.adloveyou.ms.goosegame.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the GooseToken entity.
 */
public class GooseTokenDTO implements Serializable {

    private Long id;

    private Long playerId;

    private Long whereId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getWhereId() {
        return whereId;
    }

    public void setWhereId(Long gooseCaseId) {
        this.whereId = gooseCaseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GooseTokenDTO gooseTokenDTO = (GooseTokenDTO) o;
        if(gooseTokenDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gooseTokenDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GooseTokenDTO{" +
            "id=" + getId() +
            ", playerId=" + getPlayerId() +
            "}";
    }
}
