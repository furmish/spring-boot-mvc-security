package ru.furmish.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.furmish.spring.boot_security.demo.model.User;
import ru.furmish.spring.boot_security.demo.service.UserService;

import java.security.Principal;
@RestController
@RequestMapping("/user/api/v1")
@RequiredArgsConstructor
public class RestUserControllerV1 {

    private final UserService userService;

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(final Principal principal) {
        if (userService.getUserByUsername(principal.getName()) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.getUserByUsername(principal.getName()), HttpStatus.OK);
    }
}
