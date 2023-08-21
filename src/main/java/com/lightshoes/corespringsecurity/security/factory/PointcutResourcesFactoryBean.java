package com.lightshoes.corespringsecurity.security.factory;

import com.lightshoes.corespringsecurity.service.SecurityResourceService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class PointcutResourcesFactoryBean implements FactoryBean<LinkedHashMap<String, List<ConfigAttribute>>> {

    private final SecurityResourceService securityResourceService;

    private final String resourceType;

    private LinkedHashMap<String, List<ConfigAttribute>> resourceMap;

    @Override
    public LinkedHashMap<String, List<ConfigAttribute>> getObject() {
        if (resourceMap == null) {
            init();
        }
        return resourceMap;
    }

    @Override
    public Class<?> getObjectType() {
        return LinkedHashMap.class;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }

    private void init() {
        LinkedHashMap<String, List<ConfigAttribute>> temp = new LinkedHashMap<>();
        temp.put("execution(* com.lightshoes.service.*Service.*(..))", List.of(new SecurityConfig("ROLE_USER")));
        resourceMap = temp;
//        resourceMap = securityResourceService.getPointcutResourceList();
    }
}
