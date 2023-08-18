package com.lightshoes.corespringsecurity.aopsecurity;

import com.lightshoes.corespringsecurity.domain.dto.AccountResponseDto;
import com.lightshoes.corespringsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class AopSecurityController {

    private final UserService userService;

    @GetMapping("/preAuthorize")
    @PreAuthorize("hasRole('ROLE_USER') and #account.username == principal.username")
    public String preAuthorize(AccountResponseDto account, Model model, Principal principal) {

        model.addAttribute("method", "Success @PreAuthorize");

        return  "aop/method";
    }
}
