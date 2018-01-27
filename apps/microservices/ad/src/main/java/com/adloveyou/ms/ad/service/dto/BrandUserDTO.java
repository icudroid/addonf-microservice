package com.adloveyou.ms.ad.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the BrandUser entity.
 */
public class BrandUserDTO implements Serializable {

    private Long id;

    @NotNull
    private Long userId;

    private Long brandId;

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

        BrandUserDTO brandUserDTO = (BrandUserDTO) o;
        if(brandUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), brandUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BrandUserDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            "}";
    }
}
