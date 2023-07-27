package com.lightshoes.corespringsecurity.repository;

import com.lightshoes.corespringsecurity.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Long> {
}