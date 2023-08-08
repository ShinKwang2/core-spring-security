package com.lightshoes.corespringsecurity.service.impl;

import com.lightshoes.corespringsecurity.domain.dto.RoleDto;
import com.lightshoes.corespringsecurity.domain.entity.Role;
import com.lightshoes.corespringsecurity.exception.RoleNotFound;
import com.lightshoes.corespringsecurity.repository.RoleRepository;
import com.lightshoes.corespringsecurity.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRole(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(RoleNotFound::new);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Long createRole(RoleDto request) {
        Role savdeRole = roleRepository.save(request.toEntity());
        return savdeRole.getId();
    }

    @Override
    public Long deleteRole(Long id) {
        roleRepository.deleteById(id);
        return id;
    }
}
