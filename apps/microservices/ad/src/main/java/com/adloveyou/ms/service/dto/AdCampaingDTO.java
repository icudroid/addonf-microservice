package com.adloveyou.ms.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AdCampaing entity.
 */
public class AdCampaingDTO implements Serializable {

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

        AdCampaingDTO adCampaingDTO = (AdCampaingDTO) o;
        if(adCampaingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adCampaingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdCampaingDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", initialAmount=" + getInitialAmount() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            "}";
    }
}
