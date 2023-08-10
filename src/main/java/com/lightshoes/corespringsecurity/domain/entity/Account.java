package com.lightshoes.corespringsecurity.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ACCOUNT")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    private String username;

    private String password;

    private String email;

    private Integer age;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AccountRole> accountRoles = new HashSet<>();

    @Builder
    private Account(String username, String password, String email, Integer age, Set<AccountRole> accountRoles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
    }

    public void encodePassword(String password) {
        this.password = password;
    }

    public void changeAccountRole(Set<AccountRole> roles) {
        for (AccountRole role : roles) {
            addAccountRole(role);
        }
    }

    //==양방향 연관관계 편의 메서드==//
    public void addAccountRole(AccountRole accountRole) {
        this.accountRoles.add(accountRole);
        accountRole.addAccount(this);
    }

}
