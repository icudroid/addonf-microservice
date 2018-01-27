package com.adloveyou.ms.ad.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.adloveyou.ms.ad.domain.enumeration.AdStatus;

/**
 * A DTO for the Ad entity.
 */
public class AdDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 60)
    private String name;

    @NotNull
    private BigDecimal initialAmount;

    @NotNull
    private ZonedDateTime start;

    @NotNull
    private ZonedDateTime end;

    @NotNull
    private Long duration;

    @NotNull
    private AdStatus status;

    @NotNull
    private Long adfileId;

    private Long brandId;

    private Long sectorId;

    private Long providedById;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
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

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getSectorId() {
        return sectorId;
    }

    public void setSectorId(Long sectorId) {
        this.sectorId = sectorId;
    }

    public Long getProvidedById() {
        return providedById;
    }

    public void setProvidedById(Long agencyId) {
        this.providedById = agencyId;
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
            ", name='" + getName() + "'" +
            ", initialAmount=" + getInitialAmount() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", duration=" + getDuration() +
            ", status='" + getStatus() + "'" +
            ", adfileId=" + getAdfileId() +
            "}";
    }
}
