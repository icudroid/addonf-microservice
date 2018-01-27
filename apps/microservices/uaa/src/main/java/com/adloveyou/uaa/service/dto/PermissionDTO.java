package com.adloveyou.uaa.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Permission entity.
 */
public class PermissionDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 32)
    private String name;

    @Size(max = 15)
    private String extention;

    @NotNull
    @Size(max = 256)
    private String description;

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

    public String getExtention() {
        return extention;
    }

    public void setExtention(String extention) {
        this.extention = extention;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PermissionDTO permissionDTO = (PermissionDTO) o;
        if(permissionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), permissionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PermissionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", extention='" + getExtention() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
