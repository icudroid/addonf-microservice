package com.adloveyou.ms.ad.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.adloveyou.ms.ad.domain.enumeration.AdMediaType;

/**
 * A DTO for the BidCategoryMedia entity.
 */
public class BidCategoryMediaDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal bid;

    private AdMediaType mediaType;

    private Long adId;

    private Long categoryId;

    private Long mediaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public AdMediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(AdMediaType mediaType) {
        this.mediaType = mediaType;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BidCategoryMediaDTO bidCategoryMediaDTO = (BidCategoryMediaDTO) o;
        if(bidCategoryMediaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bidCategoryMediaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BidCategoryMediaDTO{" +
            "id=" + getId() +
            ", bid=" + getBid() +
            ", mediaType='" + getMediaType() + "'" +
            "}";
    }
}
