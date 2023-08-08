package com.lightshoes.corespringsecurity.service.impl;

import com.lightshoes.corespringsecurity.domain.dto.AccountCreateDto;
import com.lightshoes.corespringsecurity.domain.dto.AccountModifyDto;
import com.lightshoes.corespringsecurity.domain.dto.AccountResponseDto;
import com.lightshoes.corespringsecurity.domain.entity.Account;
import com.lightshoes.corespringsecurity.domain.entity.AccountRole;
import com.lightshoes.corespringsecurity.domain.entity.Role;
import com.lightshoes.corespringsecurity.exception.AccountNotFound;
import com.lightshoes.corespringsecurity.repository.AccountRoleRepository;
import com.lightshoes.corespringsecurity.repository.RoleRepository;
import com.lightshoes.corespringsecurity.repository.UserRepository;
import com.lightshoes.corespringsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service("userService")
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final AccountRoleRepository accountRoleRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Long createUser(AccountCreateDto request) {

        //권한 조회 및 중개 객체 생성
        Role role = roleRepository.findByRoleName("ROLE_USER");
        AccountRole accountRole = AccountRole.createAccountRole(role);


        Account account = request.toEntity();
        account.encodePassword(passwordEncoder.encode(request.getPassword()));
        account.addAccountRole(accountRole);

        accountRoleRepository.save(accountRole);
        Account savedAccount = userRepository.save(account);
        return savedAccount.getId();
    }

    @Transactional
    @Override
    public Long modify(Long id, AccountModifyDto request) {

        Account account = userRepository.findById(id)
                .orElseThrow(AccountNotFound::new);

        if (request.getRoles() != null) {
            account.getAccountRoles().clear();
            Set<AccountRole> roles = request.getRoles().stream()
                    .map(roleName -> roleRepository.findByRoleName(roleName))
                    .map(role -> AccountRole.createAccountRole(role))
                    .collect(Collectors.toSet());

            account.changeAccountRole(roles);
//            accountRoleRepository.saveAll(roles);
        }
//        account.encodePassword(passwordEncoder.encode(request.getPassword()));

        return account.getId();
    }

    @Override
    public AccountResponseDto getAccount(Long id) {

        Account account = userRepository.findById(id)
                .orElseThrow(AccountNotFound::new);

        return AccountResponseDto.from(account);
    }

    @Override
    public List<Account> getAccounts() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public Long deleteUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }
}
