package com.adloveyou.ms.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AgencyUser entity.
 */
public class AgencyUserDTO implements Serializable {

    private Long id;

    @NotNull
    private Long userId;

    private Long agencyId;

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

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AgencyUserDTO agencyUserDTO = (AgencyUserDTO) o;
        if(agencyUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agencyUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AgencyUserDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            "}";
    }
}
