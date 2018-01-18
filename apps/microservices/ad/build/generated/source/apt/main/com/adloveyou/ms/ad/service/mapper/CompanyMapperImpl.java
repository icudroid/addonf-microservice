package com.adloveyou.ms.ad.service.mapper;

import com.adloveyou.ms.ad.domain.Company;
import com.adloveyou.ms.ad.service.dto.CompanyDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-01-17T23:25:39+0100",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_131 (Oracle Corporation)"
)
@Component
public class CompanyMapperImpl implements CompanyMapper {

    @Override
    public CompanyDTO toDto(Company entity) {
        if ( entity == null ) {
            return null;
        }

        CompanyDTO companyDTO = new CompanyDTO();

        companyDTO.setId( entity.getId() );
        companyDTO.setName( entity.getName() );
        companyDTO.setSiret( entity.getSiret() );
        companyDTO.setSiren( entity.getSiren() );
        companyDTO.setLegalStatus( entity.getLegalStatus() );
        byte[] logo = entity.getLogo();
        if ( logo != null ) {
            companyDTO.setLogo( Arrays.copyOf( logo, logo.length ) );
        }
        companyDTO.setLogoContentType( entity.getLogoContentType() );

        return companyDTO;
    }

    @Override
    public List<Company> toEntity(List<CompanyDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Company> list = new ArrayList<Company>( dtoList.size() );
        for ( CompanyDTO companyDTO : dtoList ) {
            list.add( toEntity( companyDTO ) );
        }

        return list;
    }

    @Override
    public List<CompanyDTO> toDto(List<Company> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CompanyDTO> list = new ArrayList<CompanyDTO>( entityList.size() );
        for ( Company company : entityList ) {
            list.add( toDto( company ) );
        }

        return list;
    }

    @Override
    public Company toEntity(CompanyDTO companyDTO) {
        if ( companyDTO == null ) {
            return null;
        }

        Company company = new Company();

        company.setId( companyDTO.getId() );
        company.setName( companyDTO.getName() );
        company.setSiret( companyDTO.getSiret() );
        company.setSiren( companyDTO.getSiren() );
        company.setLegalStatus( companyDTO.getLegalStatus() );
        byte[] logo = companyDTO.getLogo();
        if ( logo != null ) {
            company.setLogo( Arrays.copyOf( logo, logo.length ) );
        }
        company.setLogoContentType( companyDTO.getLogoContentType() );

        return company;
    }
}
