package com.adloveyou.ms.domain.rule;

import com.adloveyou.ms.domain.IMetaData;
import com.adloveyou.ms.domain.ad.Ad;
import com.adloveyou.ms.domain.ad.AdCampaing;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = IMetaData.TableMetadata.AD_RULE)
@DiscriminatorColumn(name = IMetaData.ColumnMetadata.AdRule.Discrimator.DISCRIMINATOR, discriminatorType = DiscriminatorType.STRING)
public abstract class AdRule implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @SequenceGenerator(name = "AdRule_Gen", sequenceName = "AdRule_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AdRule_Gen")
    protected Long id;

    @ManyToOne
    protected Ad ad;

    public Ad getAd() {
        return ad;
    }

    public AdRule ad(Ad ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
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
        AdRule adRule = (AdRule) o;
        if (adRule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adRule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
