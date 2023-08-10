package com.lightshoes.corespringsecurity.service.impl;

import com.lightshoes.corespringsecurity.domain.dto.ResourcesDto;
import com.lightshoes.corespringsecurity.domain.dto.ResourcesResponseDto;
import com.lightshoes.corespringsecurity.domain.entity.Resources;
import com.lightshoes.corespringsecurity.domain.entity.ResourcesRole;
import com.lightshoes.corespringsecurity.domain.entity.Role;
import com.lightshoes.corespringsecurity.exception.ResourcesNotFound;
import com.lightshoes.corespringsecurity.repository.ResourcesRepository;
import com.lightshoes.corespringsecurity.repository.ResourcesRoleRepository;
import com.lightshoes.corespringsecurity.repository.RoleRepository;
import com.lightshoes.corespringsecurity.service.ResourcesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ResourcesServiceImpl implements ResourcesService {

    private final ResourcesRepository resourcesRepository;

    private final ResourcesRoleRepository resourcesRoleRepository;

    private final RoleRepository roleRepository;


    @Override
    public ResourcesResponseDto getResources(Long id) {
        Resources resources = resourcesRepository.findById(id)
                .orElseThrow(ResourcesNotFound::new);

        return ResourcesResponseDto.from(resources);
    }

    @Override
    public List<ResourcesResponseDto> getResources() {
        return resourcesRepository.findAll().stream()
                .map(ResourcesResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Long createResources(ResourcesDto request) {
        Resources resources = resourcesRepository.findResourcesByResourceName(request.getResourceName())
                .orElse(request.toEntity());
        resources.getResourceRoles().clear();

        Role role = roleRepository.findByRoleName(request.getRoleName());
        ResourcesRole resourcesRole = ResourcesRole.createResourcesRole(role);
        resources.addResourcesRole(resourcesRole);

        resourcesRoleRepository.save(resourcesRole);
        Resources savedResources = resourcesRepository.save(resources);
        return savedResources.getId();
    }

    @Override
    public Long deleteResources(Long id) {
        resourcesRepository.deleteById(id);
        return id;
    }
}
