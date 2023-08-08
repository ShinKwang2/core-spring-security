package com.lightshoes.corespringsecurity.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "RESOURCES")
public class Resources {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id")
    private Long id;

    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "http_method")
    private String httpMethod;

    @Column(name = "order_num")
    private int orderNum;

    @Column(name = "resource_type")
    private String resourceType;

    @OneToMany(mappedBy = "resources", cascade = CascadeType.ALL)
    private Set<ResourcesRole> resourceRoles = new HashSet<>();

    @Builder
    private Resources(String resourceName, String httpMethod, int orderNum, String resourceType, Set<ResourcesRole> resourceRoles) {
        this.resourceName = resourceName;
        this.httpMethod = httpMethod;
        this.orderNum = orderNum;
        this.resourceType = resourceType;
    }

    //==양방향 연관관계 편의 메서드==//
    public void addResourcesRole(ResourcesRole... resourcesRole) {
        for (ResourcesRole role : resourcesRole) {
            this.resourceRoles.add(role);
            role.addResources(this);
        }
    }
}
