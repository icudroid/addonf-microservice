package com.adloveyou.ms.ad.service.mapper;

import com.adloveyou.ms.ad.domain.Contact;
import com.adloveyou.ms.ad.domain.MeanOfContact;
import com.adloveyou.ms.ad.service.dto.MeanOfContactDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-01-17T23:25:38+0100",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_131 (Oracle Corporation)"
)
@Component
public class MeanOfContactMapperImpl implements MeanOfContactMapper {

    @Autowired
    private ContactMapper contactMapper;

    @Override
    public List<MeanOfContact> toEntity(List<MeanOfContactDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<MeanOfContact> list = new ArrayList<MeanOfContact>( dtoList.size() );
        for ( MeanOfContactDTO meanOfContactDTO : dtoList ) {
            list.add( toEntity( meanOfContactDTO ) );
        }

        return list;
    }

    @Override
    public List<MeanOfContactDTO> toDto(List<MeanOfContact> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MeanOfContactDTO> list = new ArrayList<MeanOfContactDTO>( entityList.size() );
        for ( MeanOfContact meanOfContact : entityList ) {
            list.add( toDto( meanOfContact ) );
        }

        return list;
    }

    @Override
    public MeanOfContactDTO toDto(MeanOfContact meanOfContact) {
        if ( meanOfContact == null ) {
            return null;
        }

        MeanOfContactDTO meanOfContactDTO = new MeanOfContactDTO();

        Long id = meanOfContactContactId( meanOfContact );
        if ( id != null ) {
            meanOfContactDTO.setContactId( id );
        }
        meanOfContactDTO.setId( meanOfContact.getId() );
        meanOfContactDTO.setValue( meanOfContact.getValue() );
        meanOfContactDTO.setType( meanOfContact.getType() );

        return meanOfContactDTO;
    }

    @Override
    public MeanOfContact toEntity(MeanOfContactDTO meanOfContactDTO) {
        if ( meanOfContactDTO == null ) {
            return null;
        }

        MeanOfContact meanOfContact = new MeanOfContact();

        meanOfContact.setContact( contactMapper.fromId( meanOfContactDTO.getContactId() ) );
        meanOfContact.setId( meanOfContactDTO.getId() );
        meanOfContact.setValue( meanOfContactDTO.getValue() );
        meanOfContact.setType( meanOfContactDTO.getType() );

        return meanOfContact;
    }

    private Long meanOfContactContactId(MeanOfContact meanOfContact) {
        if ( meanOfContact == null ) {
            return null;
        }
        Contact contact = meanOfContact.getContact();
        if ( contact == null ) {
            return null;
        }
        Long id = contact.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
