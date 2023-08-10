package com.lightshoes.corespringsecurity.repository;

import com.lightshoes.corespringsecurity.domain.entity.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ResourcesRepository extends JpaRepository<Resources, Long> {

    Resources findByResourceNameAndHttpMethod(String resourceName, String httpMethod);

    @Query("select r from Resources r join fetch r.resourceRoles where r.resourceType = 'url' order by r.orderNum desc")
    List<Resources> findAllResources();

    @Query("select r from Resources r join fetch r.resourceRoles where r.resourceType = 'method' order by r.orderNum desc")
    List<Resources> findAllMethodResources();

    @Query("select r from Resources r join fetch r.resourceRoles where r.resourceType = 'pointcut' order by r.orderNum desc")
    List<Resources> findAllPointcutResources();

    Optional<Resources> findResourcesByResourceName(String resourceName);
}
