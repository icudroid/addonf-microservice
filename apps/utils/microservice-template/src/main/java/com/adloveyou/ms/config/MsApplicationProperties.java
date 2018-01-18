package com.adloveyou.ms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Microservice Template.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class MsApplicationProperties {


    private String threadNamePrefix = "microservice-template-Executor-";

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    public String getThreadNamePrefix() {
        return threadNamePrefix;
    }
}
