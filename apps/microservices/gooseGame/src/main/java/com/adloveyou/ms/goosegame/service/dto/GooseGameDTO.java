package com.adloveyou.ms.goosegame.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.adloveyou.ms.goosegame.domain.enumeration.GooseGameStatus;

/**
 * A DTO for the GooseGame entity.
 */
public class GooseGameDTO implements Serializable {

    private Long id;

    private String name;

    private ZonedDateTime created;

    private GooseGameStatus status;

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

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public GooseGameStatus getStatus() {
        return status;
    }

    public void setStatus(GooseGameStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GooseGameDTO gooseGameDTO = (GooseGameDTO) o;
        if(gooseGameDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gooseGameDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GooseGameDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", created='" + getCreated() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
