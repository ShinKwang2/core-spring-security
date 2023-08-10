package com.lightshoes.corespringsecurity.security.listener;

import com.lightshoes.corespringsecurity.domain.entity.*;
import com.lightshoes.corespringsecurity.repository.*;
import com.lightshoes.corespringsecurity.security.metadatasource.CustomFilterInvocationSecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRoleRepository accountRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResourcesRepository resourcesRepository;

    @Autowired
    private ResourcesRoleRepository resourcesRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccessIpRepository accessIpRepository;

    @Autowired
    FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    private static AtomicInteger count = new AtomicInteger(0);

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        setupSecurityResources();

        alreadySetup = true;
    }


    @Transactional
    public void setupSecurityResources() {
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", "관리자");
        ResourcesRole adminResourcesRole = ResourcesRole.createResourcesRole(adminRole);
        AccountRole adminAccountRole = AccountRole.createAccountRole(adminRole);
        createResourceIfNotFound("/admin/**", "", adminResourcesRole, "url");
        createResourceIfNotFound("/config", "", adminResourcesRole, "url");

        Account admin = createUserIfNotFound("admin", "pass", "admin@gmail.com", 10,  adminAccountRole);

        Role managerRole = createRoleIfNotFound("ROLE_MANAGER", "매니저");
        ResourcesRole managerResourcesRole = ResourcesRole.createResourcesRole(managerRole);
        AccountRole managerAccountRole = AccountRole.createAccountRole(managerRole);
        createResourceIfNotFound("/messages", "", managerResourcesRole, "url");
        createUserIfNotFound("manager", "pass", "manager@gmail.com", 20, managerAccountRole);

        Role userRole = createRoleIfNotFound("ROLE_USER", "사용자");
        ResourcesRole userResourcesRole = ResourcesRole.createResourcesRole(userRole);
        AccountRole userAccountRole = AccountRole.createAccountRole(userRole);
        createResourceIfNotFound("/mypage", "", userResourcesRole, "url");
        createUserIfNotFound("user", "pass", "user@gmail.com", 20, userAccountRole);

        userRole.addParentRole(managerRole);
        managerRole.addParentRole(adminRole);

        setUpAccessIpData();

        ((CustomFilterInvocationSecurityMetadataSource) filterInvocationSecurityMetadataSource).reload();

//        Set<Role> roles1 = new HashSet<>();
//
//        Role managerRole = createRoleIfNotFound("ROLE_MANAGER", "매니저");
//        roles1.add(managerRole);
//        createResourceIfNotFound("io.security.corespringsecurity.aopsecurity.method.AopMethodService.methodTest", "", roles1, "method");
//        createResourceIfNotFound("io.security.corespringsecurity.aopsecurity.method.AopMethodService.innerCallMethodTest", "", roles1, "method");
//        createResourceIfNotFound("execution(* io.security.corespringsecurity.aopsecurity.pointcut.*Service.*(..))", "", roles1, "pointcut");
//        createUserIfNotFound("manager", "pass", "manager@gmail.com", 20, roles1);
//
//        Set<Role> roles3 = new HashSet<>();
//
//        Role childRole1 = createRoleIfNotFound("ROLE_USER", "회원");
//        roles3.add(childRole1);
//        createResourceIfNotFound("/users/**", "", roles3, "url");
//        createUserIfNotFound("user", "pass", "user@gmail.com", 30, roles3);

    }

    @Transactional
    public Role createRoleIfNotFound(String roleName, String roleDesc) {

        Role role = roleRepository.findByRoleName(roleName);

        if (role == null) {
            role = Role.builder()
                    .roleName(roleName)
                    .roleDesc(roleDesc)
                    .build();
        }
        return roleRepository.save(role);
    }

    @Transactional
    public Account createUserIfNotFound(String userName, String password, String email, int age, AccountRole accountRole) {

        Account account = userRepository.findByUsername(userName);

        if (account == null) {
            account = Account.builder()
                    .username(userName)
                    .email(email)
                    .age(age)
                    .password(passwordEncoder.encode(password))
                    .build();
        }
        account.addAccountRole(accountRole);
        accountRoleRepository.save(accountRole);

        return userRepository.save(account);
    }

    @Transactional
    public Resources createResourceIfNotFound(String resourceName, String httpMethod, ResourcesRole resourcesRole, String resourceType) {
        Resources resources = resourcesRepository.findByResourceNameAndHttpMethod(resourceName, httpMethod);

        if (resources == null) {
            resources = Resources.builder()
                    .resourceName(resourceName)
                    .httpMethod(httpMethod)
                    .resourceType(resourceType)
                    .orderNum(count.incrementAndGet())
                    .build();
        }
        resources.addResourcesRole(resourcesRole);

        resourcesRoleRepository.save(resourcesRole);
        return resourcesRepository.save(resources);
    }
    
    private void setUpAccessIpData() {
        AccessIp foundAccessIp = accessIpRepository.findByIpAddress("0:0:0:0:0:0:0:1");
        if (foundAccessIp == null) {
            AccessIp accessIp = AccessIp.builder()
                    .ipAddress("0:0:0:0:0:0:0:1")
                    .build();
            accessIpRepository.save(accessIp);
        }
    }
}
