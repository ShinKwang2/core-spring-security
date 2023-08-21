package com.lightshoes.corespringsecurity.security.factory;

import com.lightshoes.corespringsecurity.service.SecurityResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@RequiredArgsConstructor
@Component
public class UrlResourcesMapFactoryBean implements FactoryBean<LinkedHashMap<RequestMatcher, List<ConfigAttribute>>> {

    private final SecurityResourceService securityResourceService;

    private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourceMap;

    @Override
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getObject() throws Exception {
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
        resourceMap = securityResourceService.getUrlResourceList();
    }
}
