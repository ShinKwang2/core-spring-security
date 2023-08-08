package com.lightshoes.corespringsecurity.domain.dto;

import com.lightshoes.corespringsecurity.domain.entity.Account;
import lombok.Data;

import java.util.List;

@Data
public class AccountCreateDto {

    private String username;

    private String password;

    private String email;

    private Integer age;

    public Account toEntity() {
        return Account.builder()
                .username(username)
                .password(password)
                .email(email)
                .age(age)
                .build();
    }
}
