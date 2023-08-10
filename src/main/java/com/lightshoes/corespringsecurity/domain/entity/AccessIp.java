package com.lightshoes.corespringsecurity.domain.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ACCESS_IP")
public class AccessIp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ip_id")
    private Long id;

    @Column(name = "ip_address")
    private String ipAddress;

    @Builder
    public AccessIp(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
