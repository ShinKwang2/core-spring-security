package com.lightshoes.corespringsecurity.domain.dto;

import com.lightshoes.corespringsecurity.domain.entity.Account;
import com.lightshoes.corespringsecurity.domain.entity.AccountRole;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class AccountResponseDto {

    private Long id;
    private String username;
    private String email;
    private Integer age;
    private List<String> roles;

    @Builder
    private AccountResponseDto(Long id, String username, String email, Integer age, List<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.age = age;
        this.roles = roles;
    }

    public static AccountResponseDto from(Account account) {
        return AccountResponseDto.builder()
                .id(account.getId())
                .username(account.getUsername())
                .email(account.getEmail())
                .age(account.getAge())
                .roles(roleToString(account.getAccountRoles()))
                .build();
    }

    private static List<String> roleToString(Set<AccountRole> roles) {
        return roles.stream()
                .map(accountRole -> accountRole.getRole().getRoleName())
                .collect(Collectors.toList());
    }
}
