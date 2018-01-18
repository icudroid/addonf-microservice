package com.adloveyou.ms.goosegame.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.adloveyou.ms.goosegame")
public class FeignConfiguration {

}
