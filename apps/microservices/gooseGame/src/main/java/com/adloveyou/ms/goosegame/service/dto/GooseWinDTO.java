package com.adloveyou.ms.goosegame.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import com.adloveyou.ms.goosegame.domain.enumeration.WinStatus;

/**
 * A DTO for the GooseWin entity.
 */
public class GooseWinDTO implements Serializable {

    private Long id;

    private Integer value;

    private WinStatus status;

    private ZonedDateTime won;

    private Long playerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public WinStatus getStatus() {
        return status;
    }

    public void setStatus(WinStatus status) {
        this.status = status;
    }

    public ZonedDateTime getWon() {
        return won;
    }

    public void setWon(ZonedDateTime won) {
        this.won = won;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GooseWinDTO gooseWinDTO = (GooseWinDTO) o;
        if(gooseWinDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gooseWinDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GooseWinDTO{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", status='" + getStatus() + "'" +
            ", won='" + getWon() + "'" +
            ", playerId=" + getPlayerId() +
            "}";
    }
}
