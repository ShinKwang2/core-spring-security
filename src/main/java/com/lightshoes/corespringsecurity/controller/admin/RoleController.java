package com.lightshoes.corespringsecurity.controller.admin;

import com.lightshoes.corespringsecurity.domain.dto.RoleDto;
import com.lightshoes.corespringsecurity.domain.entity.Role;
import com.lightshoes.corespringsecurity.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/admin/roles")
    public String getRoles(Model model) throws Exception {

        List<Role> roles = roleService.getRoles();
        model.addAttribute("roles", roles);

        return "admin/role/list";
    }

    @GetMapping("/admin/roles/register")
    public String viewRoles(Model model) throws Exception {

        RoleDto role = new RoleDto();
        model.addAttribute("role", role);

        return "admin/role/detail";
    }

    @PostMapping("/admin/roles")
    public String createRole(@ModelAttribute RoleDto roleDto) throws Exception {
        roleService.createRole(roleDto);

        return "redirect:/admin/roles";
    }

    @GetMapping("/admin/roles/{id}")
    public String getRole(@PathVariable Long id, Model model) throws Exception {

        Role role = roleService.getRole(id);
        RoleDto roleDto = RoleDto.from(role);
        model.addAttribute("role", roleDto);

        return "admin/role/detail";
    }

    @GetMapping("/admin/roles/delete/{id}")
    public String removeRole(@PathVariable Long id) throws Exception {
        roleService.deleteRole(id);

        return "redirect:/admin/resources";
    }

}
