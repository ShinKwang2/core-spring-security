package com.lightshoes.corespringsecurity.service;

import com.lightshoes.corespringsecurity.domain.dto.RoleDto;
import com.lightshoes.corespringsecurity.domain.entity.Role;

import java.util.List;

public interface RoleService {

    Role getRole(Long id);

    List<Role> getRoles();

    Long createRole(RoleDto role);

    Long deleteRole(Long id);
}
