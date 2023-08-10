package com.lightshoes.corespringsecurity.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ROLE")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_desc")
    private String roleDesc;

    @OneToMany(mappedBy = "role", cascade = CascadeType.REMOVE)
    private Set<ResourcesRole> resourceRoles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "role")
    private Set<AccountRole> accountRoles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Role parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<Role> childRoles = new HashSet<>();

    @Builder
    private Role(String roleName, String roleDesc) {
        this.roleName = roleName;
        this.roleDesc = roleDesc;
    }

    //== 연관관계 편의 메서드 ==//
    public void addAccountRole(AccountRole accountRole) {
        this.accountRoles.add(accountRole);
    }

    public void addResourcesRole(ResourcesRole resourcesRole) {
        this.resourceRoles.add(resourcesRole);
    }

    public void addParentRole(Role role) {
        parent = role;
        role.addChildRole(this);
    }

    private void addChildRole(Role role) {
        childRoles.add(role);
    }
}
