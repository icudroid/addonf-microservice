package com.adloveyou.ms.ad.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import com.adloveyou.ms.ad.domain.enumeration.LegalStatus;

/**
 * A DTO for the Media entity.
 */
public class MediaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 256)
    private String name;

    @NotNull
    @Size(min = 16)
    private String passPhrase;

    @NotNull
    private String extId;

    private String siret;

    private String siren;

    private LegalStatus legalStatus;

    @NotNull
    @Lob
    private byte[] logo;
    private String logoContentType;

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

    public String getPassPhrase() {
        return passPhrase;
    }

    public void setPassPhrase(String passPhrase) {
        this.passPhrase = passPhrase;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public String getSiren() {
        return siren;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public LegalStatus getLegalStatus() {
        return legalStatus;
    }

    public void setLegalStatus(LegalStatus legalStatus) {
        this.legalStatus = legalStatus;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MediaDTO mediaDTO = (MediaDTO) o;
        if(mediaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mediaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MediaDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", passPhrase='" + getPassPhrase() + "'" +
            ", extId='" + getExtId() + "'" +
            ", siret='" + getSiret() + "'" +
            ", siren='" + getSiren() + "'" +
            ", legalStatus='" + getLegalStatus() + "'" +
            ", logo='" + getLogo() + "'" +
            "}";
    }
}
