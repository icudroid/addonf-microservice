package com.adloveyou.ms.statistic.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.adloveyou.ms.statistic")
public class FeignConfiguration {

}
