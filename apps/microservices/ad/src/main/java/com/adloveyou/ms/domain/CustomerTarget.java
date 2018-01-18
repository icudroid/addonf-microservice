package com.adloveyou.ms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.adloveyou.ms.domain.enumeration.Sex;

import com.adloveyou.ms.domain.enumeration.AgeGroup;

/**
 * A CustomerTarget.
 */
@Entity
@Table(name = "customer_target")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customertarget")
public class CustomerTarget implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(name = "age")
    private AgeGroup age;

    @ManyToOne
    private Brand brand;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sex getSex() {
        return sex;
    }

    public CustomerTarget sex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public AgeGroup getAge() {
        return age;
    }

    public CustomerTarget age(AgeGroup age) {
        this.age = age;
        return this;
    }

    public void setAge(AgeGroup age) {
        this.age = age;
    }

    public Brand getBrand() {
        return brand;
    }

    public CustomerTarget brand(Brand brand) {
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
        CustomerTarget customerTarget = (CustomerTarget) o;
        if (customerTarget.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerTarget.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerTarget{" +
            "id=" + getId() +
            ", sex='" + getSex() + "'" +
            ", age='" + getAge() + "'" +
            "}";
    }
}
