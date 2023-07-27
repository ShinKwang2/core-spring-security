package com.lightshoes.corespringsecurity.domain;

import lombok.Data;

@Data
public class AccountDto {

    private String username;

    private String password;

    private String email;

    private Integer age;

    private String role;

    public Account toEntity() {
        return Account.builder()
                .username(username)
                .password(password)
                .email(email)
                .age(age)
                .role(role)
                .build();
    }
}
