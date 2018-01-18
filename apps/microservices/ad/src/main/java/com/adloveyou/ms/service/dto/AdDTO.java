package com.adloveyou.ms.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.adloveyou.ms.domain.enumeration.AdStatus;

/**
 * A DTO for the Ad entity.
 */
public class AdDTO implements Serializable {

    private Long id;

    private Long duration;

    private AdStatus status;

    private Long adfileId;

    private Long adCampaingId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public AdStatus getStatus() {
        return status;
    }

    public void setStatus(AdStatus status) {
        this.status = status;
    }

    public Long getAdfileId() {
        return adfileId;
    }

    public void setAdfileId(Long adfileId) {
        this.adfileId = adfileId;
    }

    public Long getAdCampaingId() {
        return adCampaingId;
    }

    public void setAdCampaingId(Long adCampaingId) {
        this.adCampaingId = adCampaingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdDTO adDTO = (AdDTO) o;
        if(adDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdDTO{" +
            "id=" + getId() +
            ", duration=" + getDuration() +
            ", status='" + getStatus() + "'" +
            ", adfileId=" + getAdfileId() +
            "}";
    }
}
