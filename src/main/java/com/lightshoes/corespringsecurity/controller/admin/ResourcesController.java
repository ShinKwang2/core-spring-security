package com.lightshoes.corespringsecurity.controller.admin;

import com.lightshoes.corespringsecurity.domain.dto.ResourcesDto;
import com.lightshoes.corespringsecurity.domain.dto.ResourcesResponseDto;
import com.lightshoes.corespringsecurity.domain.entity.Resources;
import com.lightshoes.corespringsecurity.domain.entity.Role;
import com.lightshoes.corespringsecurity.repository.RoleRepository;
import com.lightshoes.corespringsecurity.security.metadatasource.CustomFilterInvocationSecurityMetadataSource;
import com.lightshoes.corespringsecurity.service.ResourcesService;
import com.lightshoes.corespringsecurity.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class ResourcesController {

    private final ResourcesService resourceService;

    private final RoleRepository roleRepository;

    private final RoleService roleService;

    private final CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;

    @GetMapping("/admin/resources")
    public String getResources(Model model) throws Exception {

        List<ResourcesResponseDto> resources = resourceService.getResources();
        model.addAttribute("resources", resources);

        return "admin/resource/list";
    }

    @PostMapping("/admin/resources")
    public String createResources(ResourcesDto resourcesDto) throws Exception {
        resourceService.createResources(resourcesDto);
        customFilterInvocationSecurityMetadataSource.reload();

        return "redirect:/admin/resources";

    }

    @GetMapping("/admin/resources/register")
    public String viewRoles(Model model) throws Exception {

        List<Role> roles = roleService.getRoles();
        model.addAttribute("roleList", roles);

        ResourcesDto resourcesDto = new ResourcesDto();
        Set<String> roleSet = new HashSet<>();
        roleSet.add("");
        resourcesDto.setRoleSet(roleSet);
        model.addAttribute("resources", resourcesDto);

        return "admin/resource/detail";
    }

    @GetMapping("/admin/resources/{id}")
    public String getResources(@PathVariable Long id, Model model) throws Exception {
        ResourcesResponseDto resources = resourceService.getResources(id);
        List<Role> roles = roleService.getRoles();

        model.addAttribute("resources", resources);
        model.addAttribute("roleList", roles);

        return "admin/resource/detail";
    }

    @GetMapping("/admin/resources/delete/{id}")
    public String removeResources(@PathVariable Long id, Model model) throws Exception {
        resourceService.deleteResources(id);
        customFilterInvocationSecurityMetadataSource.reload();

        return "redirect:/admin/resources";
    }

}
