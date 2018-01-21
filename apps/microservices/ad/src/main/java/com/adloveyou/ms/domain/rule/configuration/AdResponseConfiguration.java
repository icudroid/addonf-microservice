package com.adloveyou.ms.domain.rule.configuration;

import com.adloveyou.ms.domain.IMetaData;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = IMetaData.TableMetadata.AD_RESPONSE)
public class AdResponseConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "AdResponse_Gen", sequenceName = "AdResponse_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AdResponse_Gen")
    private Long id;
    @Column(name = IMetaData.ColumnMetadata.AdResponse.RESPONSE)
    private String response;
    @Column(name = IMetaData.ColumnMetadata.AdResponse.RESPONSE_IMG)
    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
