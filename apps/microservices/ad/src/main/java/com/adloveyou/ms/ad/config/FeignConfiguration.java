package com.adloveyou.ms.ad.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.adloveyou.ms.ad")
public class FeignConfiguration {

}
