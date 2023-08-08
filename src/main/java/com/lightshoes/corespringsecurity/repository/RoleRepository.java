package com.lightshoes.corespringsecurity.repository;

import com.lightshoes.corespringsecurity.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String name);
}
