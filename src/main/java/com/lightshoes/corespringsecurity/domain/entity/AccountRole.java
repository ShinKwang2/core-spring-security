package com.lightshoes.corespringsecurity.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "account_role")
public class AccountRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_role_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    //==양방향 연관관계 편의 메서드==//
    public void addAccount(Account account) {
        this.account = account;
    }

    //생성 메서드를 위한 메서드
    public void addRole(Role role) {
        this.role = role;
        role.addAccountRole(this);
    }

    //==생성 메서드==//
    public static AccountRole createAccountRole(Role role) {
        AccountRole accountRole = new AccountRole();
        accountRole.addRole(role);
        return accountRole;
    }

}
