package ru.furmish.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebPageController {

    @GetMapping("/admin")
    public String printUsers() {
        return "index";
    }

    @GetMapping(value = "/user/{username}")
    public String printUserInfo(@PathVariable String username) {
        return "user";
    }
}
