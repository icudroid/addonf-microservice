package com.adloveyou.ms.game.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AdScore entity.
 */
public class AdScoreDTO implements Serializable {

    private Long id;

    private Integer score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdScoreDTO adScoreDTO = (AdScoreDTO) o;
        if(adScoreDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adScoreDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdScoreDTO{" +
            "id=" + getId() +
            ", score=" + getScore() +
            "}";
    }
}
