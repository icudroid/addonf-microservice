package com.adloveyou.ms.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MediaUser entity.
 */
public class MediaUserDTO implements Serializable {

    private Long id;

    private Long userId;

    private Long mediaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

        MediaUserDTO mediaUserDTO = (MediaUserDTO) o;
        if(mediaUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mediaUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MediaUserDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            "}";
    }
}
