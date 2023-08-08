package com.lightshoes.corespringsecurity.domain.dto;

import com.lightshoes.corespringsecurity.domain.entity.Resources;
import com.lightshoes.corespringsecurity.domain.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class ResourcesDto {

    private Long id;
    private String resourceName;
    private String httpMethod;
    private Integer orderNum;
    private String resourceType;
    private String roleName;
    private Set<String> roleSet;

    public Resources toEntity() {
        return Resources.builder()
                .resourceName(resourceName)
                .httpMethod(httpMethod)
                .orderNum(orderNum)
                .resourceType(resourceType)
                .build();
    }
}
