package com.adloveyou.ms.service.mapper;

import com.adloveyou.ms.domain.ad.CustomerTarget;
import com.adloveyou.ms.service.dto.CustomerTargetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CustomerTarget and its DTO CustomerTargetDTO.
 */
@Mapper(componentModel = "spring", uses = {BrandMapper.class})
public interface CustomerTargetMapper extends EntityMapper<CustomerTargetDTO, CustomerTarget> {

    @Mapping(source = "brand.id", target = "brandId")
    CustomerTargetDTO toDto(CustomerTarget customerTarget);

    @Mapping(source = "brandId", target = "brand")
    CustomerTarget toEntity(CustomerTargetDTO customerTargetDTO);

    default CustomerTarget fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerTarget customerTarget = new CustomerTarget();
        customerTarget.setId(id);
        return customerTarget;
    }
}
