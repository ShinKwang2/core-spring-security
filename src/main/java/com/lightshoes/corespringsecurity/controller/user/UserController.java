package com.lightshoes.corespringsecurity.controller.user;

import com.lightshoes.corespringsecurity.domain.entity.Account;
import com.lightshoes.corespringsecurity.domain.dto.AccountCreateDto;
import com.lightshoes.corespringsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/mypage")
    public String mypage() throws Exception {

        userService.order();

        return "user/mypage";
    }

    @GetMapping("/users")
    public String createUser() {
        return "user/login/register";
    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute AccountCreateDto accountCreateDto) {
        userService.createUser(accountCreateDto);

        return "redirect:/";
    }
}
