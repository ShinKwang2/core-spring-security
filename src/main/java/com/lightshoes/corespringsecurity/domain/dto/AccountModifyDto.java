package com.lightshoes.corespringsecurity.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountModifyDto {

    private Long id;
    private String username;
    private String email;
    private Integer age;
    private List<String> roles;


}
