package com.lightshoes.corespringsecurity.domain.dto;

import com.lightshoes.corespringsecurity.domain.entity.Resources;
import com.lightshoes.corespringsecurity.domain.entity.ResourcesRole;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ResourcesResponseDto {

    private Long id;
    private String resourceName;
    private String httpMethod;
    private Integer orderNum;
    private String resourceType;
    private Set<String> roleSet;

    @Builder
    private ResourcesResponseDto(Long id, String resourceName,
                                String httpMethod, Integer orderNum,
                                String resourceType, Set<String> roleSet) {
        this.id = id;
        this.resourceName = resourceName;
        this.httpMethod = httpMethod;
        this.orderNum = orderNum;
        this.resourceType = resourceType;
        this.roleSet = roleSet;
    }

    public static ResourcesResponseDto from(Resources resources) {
        return ResourcesResponseDto.builder()
                .id(resources.getId())
                .resourceName(resources.getResourceName())
                .httpMethod(resources.getHttpMethod())
                .orderNum(resources.getOrderNum())
                .resourceType(resources.getResourceType())
                .roleSet(roleToString(resources.getResourceRoles()))
                .build();
    }

    private static Set<String> roleToString(Set<ResourcesRole> roles) {
        return roles.stream()
                .map(role -> role.getRole().getRoleName())
                .collect(Collectors.toSet());
    }
}
