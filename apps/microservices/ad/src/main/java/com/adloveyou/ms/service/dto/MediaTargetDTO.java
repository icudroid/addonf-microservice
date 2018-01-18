package com.adloveyou.ms.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.adloveyou.ms.domain.enumeration.AdMediaType;

/**
 * A DTO for the MediaTarget entity.
 */
public class MediaTargetDTO implements Serializable {

    private Long id;

    private AdMediaType mediaType;

    private Long brandId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdMediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(AdMediaType mediaType) {
        this.mediaType = mediaType;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MediaTargetDTO mediaTargetDTO = (MediaTargetDTO) o;
        if(mediaTargetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mediaTargetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MediaTargetDTO{" +
            "id=" + getId() +
            ", mediaType='" + getMediaType() + "'" +
            "}";
    }
}
