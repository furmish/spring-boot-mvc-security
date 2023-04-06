package ru.furmish.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.furmish.spring.boot_security.demo.model.User;
import ru.furmish.spring.boot_security.demo.service.RoleService;
import ru.furmish.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
public class WebPageController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public WebPageController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String printUsers(@ModelAttribute(value = "newUser") User user, ModelMap model, Principal principal) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("user", userService.getUserByUsername(principal.getName()));
        model.addAttribute("rolesList", roleService.getRolesList());
        return "index";
    }

    @GetMapping(value = "/user/{username}")
    public String printUserInfo(@PathVariable String username, ModelMap model) {
        model.addAttribute("user", userService.getUserByUsername(username));
        return "user";
    }
}
