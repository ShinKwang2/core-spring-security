package com.lightshoes.corespringsecurity.security.service;

import com.lightshoes.corespringsecurity.domain.entity.Account;
import com.lightshoes.corespringsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service("userDetailsService")
public class CustomDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Account account = userRepository.findByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        account.getAccountRoles().stream()
                .map(accountRole -> accountRole.getRole().getRoleName())
                .collect(Collectors.toList())
                .forEach(string -> roles.add(new SimpleGrantedAuthority(string)));

        AccountContext accountContext = new AccountContext(account, roles);

        return accountContext;
    }
}
