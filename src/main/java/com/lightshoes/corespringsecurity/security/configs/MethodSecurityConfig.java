package com.lightshoes.corespringsecurity.security.configs;

import com.lightshoes.corespringsecurity.security.factory.MethodResourcesFactoryBean;
import com.lightshoes.corespringsecurity.service.SecurityResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@RequiredArgsConstructor
@Configuration
@EnableGlobalMethodSecurity
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    private final MethodResourcesFactoryBean methodResourcesFactoryBean;

    @Override
    protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
        return mapBasedMethodSecurityMetadataSource();
    }

    @Bean
    public MapBasedMethodSecurityMetadataSource mapBasedMethodSecurityMetadataSource() {
        try {
            return new MapBasedMethodSecurityMetadataSource(methodResourcesFactoryBean.getObject());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
