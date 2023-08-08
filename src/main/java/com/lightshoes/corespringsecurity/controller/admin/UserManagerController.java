package com.lightshoes.corespringsecurity.controller.admin;

import com.lightshoes.corespringsecurity.domain.dto.AccountModifyDto;
import com.lightshoes.corespringsecurity.domain.dto.AccountResponseDto;
import com.lightshoes.corespringsecurity.domain.entity.Account;
import com.lightshoes.corespringsecurity.domain.entity.Role;
import com.lightshoes.corespringsecurity.service.RoleService;
import com.lightshoes.corespringsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserManagerController {

    private final UserService userService;

    private final RoleService roleService;

    @GetMapping("/admin/accounts")
    public String getUsers(Model model) throws Exception {

        List<Account> accounts = userService.getAccounts();
        model.addAttribute("accounts", accounts);

        return "admin/user/list";
    }

    @PostMapping("admin/accounts")
    public String modifyUser(AccountModifyDto accountModifyDto) throws Exception {
        userService.modify(accountModifyDto.getId(), accountModifyDto);

        return "redirect:/admin/accounts";
    }

    @GetMapping(value = "/admin/accounts/{id}")
    public String getUser(@PathVariable(value = "id") Long id, Model model) {

        AccountResponseDto accountDto = userService.getAccount(id);
        List<Role> roleList = roleService.getRoles();

        model.addAttribute("account", accountDto);
        model.addAttribute("roleList", roleList);

        return "admin/user/detail";
    }
}
