package com.adloveyou.ms.ad.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AdConfiguration entity.
 */
public class AdConfigurationDTO implements Serializable {

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

        AdConfigurationDTO adConfigurationDTO = (AdConfigurationDTO) o;
        if(adConfigurationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adConfigurationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdConfigurationDTO{" +
            "id=" + getId() +
            "}";
    }
}
