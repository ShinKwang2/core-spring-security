package com.lightshoes.corespringsecurity.security.configs;

import com.lightshoes.corespringsecurity.security.factory.MethodResourcesFactoryBean;
import com.lightshoes.corespringsecurity.security.factory.PointcutResourcesFactoryBean;
import com.lightshoes.corespringsecurity.service.SecurityResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
@EnableGlobalMethodSecurity
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    private final SecurityResourceService securityResourceService;

    @Override
    protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
        return mapBasedMethodSecurityMetadataSource();
    }

    @Bean
    public MapBasedMethodSecurityMetadataSource mapBasedMethodSecurityMetadataSource() {
        return new MapBasedMethodSecurityMetadataSource(methodResourcesFactoryBean().getObject());
    }

    @Bean
    public MethodResourcesFactoryBean methodResourcesFactoryBean() {
        return new MethodResourcesFactoryBean(securityResourceService, "method");
    }

    @Bean
    public PointcutResourcesFactoryBean pointCutResourcesMapFactoryBean() {
        return new PointcutResourcesFactoryBean(securityResourceService, "pointcut");
    }

    @Bean
    BeanPostProcessor protectPointcutPostProcessor() throws Exception {

        Class<?> clazz = Class.forName("org.springframework.security.config.method.ProtectPointcutPostProcessor");
        Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(MapBasedMethodSecurityMetadataSource.class);
        declaredConstructor.setAccessible(true);
        Object instance = declaredConstructor.newInstance(new MapBasedMethodSecurityMetadataSource());


        Method setPointcutMap = instance.getClass().getMethod("setPointcutMap", Map.class);
        setPointcutMap.setAccessible(true);

        LinkedHashMap<String, List<ConfigAttribute>> temp = new LinkedHashMap<>();
        temp.put("execution(* com.lightshoes.service.*Service.*(..))", List.of(new SecurityConfig("ROLE_USER")));

        setPointcutMap.invoke(instance, temp);

        return (BeanPostProcessor) instance;
    }
}
