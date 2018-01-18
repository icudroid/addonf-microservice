package com.adloveyou.ms.ad.service.mapper;

import com.adloveyou.ms.ad.domain.Company;
import com.adloveyou.ms.ad.domain.FileAttachement;
import com.adloveyou.ms.ad.service.dto.FileAttachementDTO;
import java.util.ArrayList;
import java.util.Arrays;
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
public class FileAttachementMapperImpl implements FileAttachementMapper {

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public List<FileAttachement> toEntity(List<FileAttachementDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<FileAttachement> list = new ArrayList<FileAttachement>( dtoList.size() );
        for ( FileAttachementDTO fileAttachementDTO : dtoList ) {
            list.add( toEntity( fileAttachementDTO ) );
        }

        return list;
    }

    @Override
    public List<FileAttachementDTO> toDto(List<FileAttachement> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FileAttachementDTO> list = new ArrayList<FileAttachementDTO>( entityList.size() );
        for ( FileAttachement fileAttachement : entityList ) {
            list.add( toDto( fileAttachement ) );
        }

        return list;
    }

    @Override
    public FileAttachementDTO toDto(FileAttachement fileAttachement) {
        if ( fileAttachement == null ) {
            return null;
        }

        FileAttachementDTO fileAttachementDTO = new FileAttachementDTO();

        Long id = fileAttachementCompanyId( fileAttachement );
        if ( id != null ) {
            fileAttachementDTO.setCompanyId( id );
        }
        fileAttachementDTO.setId( fileAttachement.getId() );
        byte[] file = fileAttachement.getFile();
        if ( file != null ) {
            fileAttachementDTO.setFile( Arrays.copyOf( file, file.length ) );
        }
        fileAttachementDTO.setFileContentType( fileAttachement.getFileContentType() );

        return fileAttachementDTO;
    }

    @Override
    public FileAttachement toEntity(FileAttachementDTO fileAttachementDTO) {
        if ( fileAttachementDTO == null ) {
            return null;
        }

        FileAttachement fileAttachement = new FileAttachement();

        fileAttachement.setCompany( companyMapper.fromId( fileAttachementDTO.getCompanyId() ) );
        fileAttachement.setId( fileAttachementDTO.getId() );
        byte[] file = fileAttachementDTO.getFile();
        if ( file != null ) {
            fileAttachement.setFile( Arrays.copyOf( file, file.length ) );
        }
        fileAttachement.setFileContentType( fileAttachementDTO.getFileContentType() );

        return fileAttachement;
    }

    private Long fileAttachementCompanyId(FileAttachement fileAttachement) {
        if ( fileAttachement == null ) {
            return null;
        }
        Company company = fileAttachement.getCompany();
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
