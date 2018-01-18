package com.adloveyou.ms.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AdRule entity.
 */
public class AdRuleDTO implements Serializable {

    private Long id;

    private Long adId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdRuleDTO adRuleDTO = (AdRuleDTO) o;
        if(adRuleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adRuleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdRuleDTO{" +
            "id=" + getId() +
            "}";
    }
}
