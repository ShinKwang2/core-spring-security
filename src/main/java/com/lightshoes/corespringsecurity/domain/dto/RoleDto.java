package com.lightshoes.corespringsecurity.domain.dto;

import com.lightshoes.corespringsecurity.domain.entity.Role;
import lombok.Data;

@Data
public class RoleDto {

    private String roleName;
    private String roleDesc;

    public Role toEntity() {
        return Role.builder()
                .roleName(roleName)
                .roleDesc(roleDesc)
                .build();
    }

    public static RoleDto from(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleName(role.getRoleName());
        roleDto.setRoleDesc(role.getRoleDesc());
        return roleDto;
    }
}
