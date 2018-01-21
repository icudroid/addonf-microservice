package com.adloveyou.ms.service.dto.rule.configuration;

import java.io.Serializable;


public class AdResponseConfigurationDTO implements Serializable {
    private Long id;
    private String response;
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
