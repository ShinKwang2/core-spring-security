package com.lightshoes.corespringsecurity.security.init;

import com.lightshoes.corespringsecurity.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.stereotype.Component;

@Component
public class SecurityInitializer implements ApplicationRunner {

    @Autowired
    private RoleHierarchyImpl roleHierarchy;

    @Autowired
    private RoleService roleService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String allHierarchy = roleService.findAllHierarchy();
        roleHierarchy.setHierarchy(allHierarchy);
    }
}
