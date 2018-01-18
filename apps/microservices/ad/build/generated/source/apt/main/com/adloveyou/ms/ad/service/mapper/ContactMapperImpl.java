package com.adloveyou.ms.ad.service.mapper;

import com.adloveyou.ms.ad.domain.Company;
import com.adloveyou.ms.ad.domain.Contact;
import com.adloveyou.ms.ad.service.dto.ContactDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-01-17T23:25:39+0100",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_131 (Oracle Corporation)"
)
@Component
public class ContactMapperImpl implements ContactMapper {

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public List<Contact> toEntity(List<ContactDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Contact> list = new ArrayList<Contact>( dtoList.size() );
        for ( ContactDTO contactDTO : dtoList ) {
            list.add( toEntity( contactDTO ) );
        }

        return list;
    }

    @Override
    public List<ContactDTO> toDto(List<Contact> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ContactDTO> list = new ArrayList<ContactDTO>( entityList.size() );
        for ( Contact contact : entityList ) {
            list.add( toDto( contact ) );
        }

        return list;
    }

    @Override
    public ContactDTO toDto(Contact contact) {
        if ( contact == null ) {
            return null;
        }

        ContactDTO contactDTO = new ContactDTO();

        Long id = contactCompanyId( contact );
        if ( id != null ) {
            contactDTO.setCompanyId( id );
        }
        contactDTO.setId( contact.getId() );
        contactDTO.setLastname( contact.getLastname() );
        contactDTO.setFirstname( contact.getFirstname() );

        return contactDTO;
    }

    @Override
    public Contact toEntity(ContactDTO contactDTO) {
        if ( contactDTO == null ) {
            return null;
        }

        Contact contact = new Contact();

        contact.setCompany( companyMapper.fromId( contactDTO.getCompanyId() ) );
        contact.setId( contactDTO.getId() );
        contact.setLastname( contactDTO.getLastname() );
        contact.setFirstname( contactDTO.getFirstname() );

        return contact;
    }

    private Long contactCompanyId(Contact contact) {
        if ( contact == null ) {
            return null;
        }
        Company company = contact.getCompany();
        if ( company == null ) {
            return null;
        }
        Long id = company.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
