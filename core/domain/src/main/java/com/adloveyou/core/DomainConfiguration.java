package com.adloveyou.core;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.adloveyou.core.repository")
@EnableTransactionManagement
@EntityScan
@ComponentScan
public class DomainConfiguration {

}
