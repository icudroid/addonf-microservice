package com.adloveyou.ms.game.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AdChoise entity.
 */
public class AdChoiseDTO implements Serializable {

    private Long id;

    private Integer number;

    private String question;

    private Long bidId;

    private Long adConfigId;

    private Long adGameId;

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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Long getBidId() {
        return bidId;
    }

    public void setBidId(Long bidId) {
        this.bidId = bidId;
    }

    public Long getAdConfigId() {
        return adConfigId;
    }

    public void setAdConfigId(Long adConfigId) {
        this.adConfigId = adConfigId;
    }

    public Long getAdGameId() {
        return adGameId;
    }

    public void setAdGameId(Long adGameId) {
        this.adGameId = adGameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdChoiseDTO adChoiseDTO = (AdChoiseDTO) o;
        if(adChoiseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adChoiseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdChoiseDTO{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", question='" + getQuestion() + "'" +
            ", bidId=" + getBidId() +
            ", adConfigId=" + getAdConfigId() +
            "}";
    }
}
