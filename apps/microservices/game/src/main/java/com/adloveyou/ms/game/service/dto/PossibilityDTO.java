package com.adloveyou.ms.game.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Possibility entity.
 */
public class PossibilityDTO implements Serializable {

    private Long id;

    private Long adId;

    private Long correctId;

    private Long possibleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public Long getCorrectId() {
        return correctId;
    }

    public void setCorrectId(Long adChoiseId) {
        this.correctId = adChoiseId;
    }

    public Long getPossibleId() {
        return possibleId;
    }

    public void setPossibleId(Long adChoiseId) {
        this.possibleId = adChoiseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PossibilityDTO possibilityDTO = (PossibilityDTO) o;
        if(possibilityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), possibilityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PossibilityDTO{" +
            "id=" + getId() +
            ", adId=" + getAdId() +
            "}";
    }
}
