package com.lightshoes.corespringsecurity.service;

import com.lightshoes.corespringsecurity.domain.entity.Resources;
import com.lightshoes.corespringsecurity.repository.ResourcesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SecurityResourceService {

    private final ResourcesRepository resourcesRepository;

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {

        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<Resources> resources = resourcesRepository.findAllResources();

        resources.forEach(resource -> {
            List<ConfigAttribute> configAttributes = new ArrayList<>();
            resource.getResourceRoles().forEach(resourcesRole -> {
                configAttributes.add(new SecurityConfig(resourcesRole.getRole().getRoleName()));
            });
            result.put(new AntPathRequestMatcher(resource.getResourceName()), configAttributes);
        });

        return result;
    }
}
