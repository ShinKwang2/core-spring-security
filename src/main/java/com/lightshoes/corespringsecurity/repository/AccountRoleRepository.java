package com.lightshoes.corespringsecurity.repository;

import com.lightshoes.corespringsecurity.domain.entity.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {
}
