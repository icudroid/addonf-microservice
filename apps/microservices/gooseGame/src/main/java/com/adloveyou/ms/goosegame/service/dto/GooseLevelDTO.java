package com.adloveyou.ms.goosegame.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the GooseLevel entity.
 */
public class GooseLevelDTO implements Serializable {

    private Long id;

    private Integer level;

    private Long limitedTime;

    private Integer nbMaxAdByPlay;

    private Long gooseGameId;

    private Long startId;

    private Long endId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getLimitedTime() {
        return limitedTime;
    }

    public void setLimitedTime(Long limitedTime) {
        this.limitedTime = limitedTime;
    }

    public Integer getNbMaxAdByPlay() {
        return nbMaxAdByPlay;
    }

    public void setNbMaxAdByPlay(Integer nbMaxAdByPlay) {
        this.nbMaxAdByPlay = nbMaxAdByPlay;
    }

    public Long getGooseGameId() {
        return gooseGameId;
    }

    public void setGooseGameId(Long gooseGameId) {
        this.gooseGameId = gooseGameId;
    }

    public Long getStartId() {
        return startId;
    }

    public void setStartId(Long gooseCaseId) {
        this.startId = gooseCaseId;
    }

    public Long getEndId() {
        return endId;
    }

    public void setEndId(Long gooseCaseId) {
        this.endId = gooseCaseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GooseLevelDTO gooseLevelDTO = (GooseLevelDTO) o;
        if(gooseLevelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gooseLevelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GooseLevelDTO{" +
            "id=" + getId() +
            ", level=" + getLevel() +
            ", limitedTime=" + getLimitedTime() +
            ", nbMaxAdByPlay=" + getNbMaxAdByPlay() +
            "}";
    }
}
