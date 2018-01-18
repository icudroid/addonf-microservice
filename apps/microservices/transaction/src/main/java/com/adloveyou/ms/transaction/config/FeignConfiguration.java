package com.adloveyou.ms.transaction.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.adloveyou.ms.transaction")
public class FeignConfiguration {

}
