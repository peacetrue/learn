package com.github.peacetrue.learn.dubbo;

import org.apache.dubbo.apidocs.EnableDubboApiDocs;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableDubboApiDocs
@Configuration
public class DubboDocConfig {

    @Bean
    @ConditionalOnMissingBean
    public ApplicationConfig application() {
        return new ApplicationConfig();
    }

    @Bean
    @ConditionalOnMissingBean
    public RegistryConfig registry() {
        return new RegistryConfig();
    }

    @Bean
    @ConditionalOnMissingBean
    public ProtocolConfig protocol() {
        return new ProtocolConfig();
    }

}

