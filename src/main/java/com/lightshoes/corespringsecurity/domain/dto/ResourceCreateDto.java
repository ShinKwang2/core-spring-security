package com.lightshoes.corespringsecurity.domain.dto;

import com.lightshoes.corespringsecurity.domain.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class ResourceCreateDto {

    private String resourceName;
    private String httpMethod;
    private Integer orderNum;
    private String resourceType;
    private String roleName;
    private Set<Role> roleSet;
}
