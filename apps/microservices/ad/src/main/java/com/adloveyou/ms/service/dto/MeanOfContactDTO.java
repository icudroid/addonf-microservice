package com.adloveyou.ms.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.adloveyou.ms.domain.enumeration.MeanOfContactType;

/**
 * A DTO for the MeanOfContact entity.
 */
public class MeanOfContactDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 60)
    private String value;

    @NotNull
    private MeanOfContactType type;

    private Long contactId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MeanOfContactType getType() {
        return type;
    }

    public void setType(MeanOfContactType type) {
        this.type = type;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MeanOfContactDTO meanOfContactDTO = (MeanOfContactDTO) o;
        if(meanOfContactDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), meanOfContactDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MeanOfContactDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
