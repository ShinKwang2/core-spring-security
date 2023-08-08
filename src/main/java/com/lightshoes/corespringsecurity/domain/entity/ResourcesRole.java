package com.lightshoes.corespringsecurity.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "resources_role")
public class ResourcesRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resources_role_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    private Resources resources;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    //==양방향 연관관계 편의 메서드==//
    public void addResources(Resources resources) {
        this.resources = resources;
    }

    //생성 메서드를 위한 메서드
    public void addRole(Role role) {
        this.role = role;
        role.addResourcesRole(this);
    }

    //==생성 메서드==//
    public static ResourcesRole createResourcesRole(Role role) {
        ResourcesRole resourcesRole = new ResourcesRole();
        resourcesRole.addRole(role);
        return resourcesRole;
    }
}
