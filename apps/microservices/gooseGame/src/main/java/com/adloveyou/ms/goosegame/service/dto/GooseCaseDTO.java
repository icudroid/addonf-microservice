package com.adloveyou.ms.goosegame.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the GooseCase entity.
 */
public class GooseCaseDTO implements Serializable {

    private Long id;

    private String name;

    private Long gooseLevelId;

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

    public Long getGooseLevelId() {
        return gooseLevelId;
    }

    public void setGooseLevelId(Long gooseLevelId) {
        this.gooseLevelId = gooseLevelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GooseCaseDTO gooseCaseDTO = (GooseCaseDTO) o;
        if(gooseCaseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gooseCaseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GooseCaseDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
