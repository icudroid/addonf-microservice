package com.adloveyou.ms.game.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AdPlayerResponse entity.
 */
public class AdPlayerResponseDTO implements Serializable {

    private Long id;

    private Integer number;

    private Boolean correctAnswer;

    private Boolean played;

    private Long adScoreId;

    private Set<PossibilityDTO> responses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Boolean isPlayed() {
        return played;
    }

    public void setPlayed(Boolean played) {
        this.played = played;
    }

    public Long getAdScoreId() {
        return adScoreId;
    }

    public void setAdScoreId(Long adScoreId) {
        this.adScoreId = adScoreId;
    }

    public Set<PossibilityDTO> getResponses() {
        return responses;
    }

    public void setResponses(Set<PossibilityDTO> possibilities) {
        this.responses = possibilities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdPlayerResponseDTO adPlayerResponseDTO = (AdPlayerResponseDTO) o;
        if(adPlayerResponseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adPlayerResponseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdPlayerResponseDTO{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", correctAnswer='" + isCorrectAnswer() + "'" +
            ", played='" + isPlayed() + "'" +
            "}";
    }
}
