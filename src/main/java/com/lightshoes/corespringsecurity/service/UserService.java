package com.lightshoes.corespringsecurity.service;

import com.lightshoes.corespringsecurity.domain.dto.AccountCreateDto;
import com.lightshoes.corespringsecurity.domain.dto.AccountModifyDto;
import com.lightshoes.corespringsecurity.domain.dto.AccountResponseDto;
import com.lightshoes.corespringsecurity.domain.entity.Account;

import java.util.List;

public interface UserService {

    Long createUser(AccountCreateDto accountCreateDto);

    Long modify(Long id, AccountModifyDto accountModifyDto);

    AccountResponseDto getAccount(Long id);

    List<Account> getAccounts();

    Long deleteUser(Long id);
}
