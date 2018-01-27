package com.adloveyou.ms.ad.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AdRestriction entity.
 */
public class AdRestrictionDTO implements Serializable {

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

        AdRestrictionDTO adRestrictionDTO = (AdRestrictionDTO) o;
        if(adRestrictionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adRestrictionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdRestrictionDTO{" +
            "id=" + getId() +
            "}";
    }
}
