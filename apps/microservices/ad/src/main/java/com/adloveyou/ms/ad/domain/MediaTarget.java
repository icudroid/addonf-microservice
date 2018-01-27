package com.adloveyou.ms.ad.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.adloveyou.ms.ad.domain.enumeration.AdMediaType;

/**
 * A MediaTarget.
 */
@Entity
@Table(name = "media_target")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mediatarget")
public class MediaTarget implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "media_type", nullable = false)
    private AdMediaType mediaType;

    @ManyToOne
    private Brand brand;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdMediaType getMediaType() {
        return mediaType;
    }

    public MediaTarget mediaType(AdMediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public void setMediaType(AdMediaType mediaType) {
        this.mediaType = mediaType;
    }

    public Brand getBrand() {
        return brand;
    }

    public MediaTarget brand(Brand brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MediaTarget mediaTarget = (MediaTarget) o;
        if (mediaTarget.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mediaTarget.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MediaTarget{" +
            "id=" + getId() +
            ", mediaType='" + getMediaType() + "'" +
            "}";
    }
}
