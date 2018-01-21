package com.adloveyou.ms.service.mapper;

import com.adloveyou.ms.domain.contact.Contact;
import com.adloveyou.ms.service.dto.ContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Contact and its DTO ContactDTO.
 */
@Mapper(componentModel = "spring", uses = {BrandMapper.class, AgencyMapper.class, MediaMapper.class})
public interface ContactMapper extends EntityMapper<ContactDTO, Contact> {

    @Mapping(source = "brand.id", target = "brandId")
    @Mapping(source = "agency.id", target = "agencyId")
    @Mapping(source = "media.id", target = "mediaId")
    ContactDTO toDto(Contact contact);

    @Mapping(source = "brandId", target = "brand")
    @Mapping(source = "agencyId", target = "agency")
    @Mapping(source = "mediaId", target = "media")
    @Mapping(target = "meanOfContacts", ignore = true)
    Contact toEntity(ContactDTO contactDTO);

    default Contact fromId(Long id) {
        if (id == null) {
            return null;
        }
        Contact contact = new Contact();
        contact.setId(id);
        return contact;
    }
}
