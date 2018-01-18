package com.adloveyou.ms.billing.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.adloveyou.ms.billing")
public class FeignConfiguration {

}
