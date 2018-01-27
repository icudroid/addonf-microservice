package com.adloveyou.ms.ad.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.adloveyou.ms.ad.domain.enumeration.Sex;
import com.adloveyou.ms.ad.domain.enumeration.AgeGroup;

/**
 * A DTO for the CustomerTarget entity.
 */
public class CustomerTargetDTO implements Serializable {

    private Long id;

    private Sex sex;

    private AgeGroup age;

    private Long brandId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public AgeGroup getAge() {
        return age;
    }

    public void setAge(AgeGroup age) {
        this.age = age;
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

        CustomerTargetDTO customerTargetDTO = (CustomerTargetDTO) o;
        if(customerTargetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerTargetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerTargetDTO{" +
            "id=" + getId() +
            ", sex='" + getSex() + "'" +
            ", age='" + getAge() + "'" +
            "}";
    }
}
