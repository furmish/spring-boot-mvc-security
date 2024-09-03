package ru.furmish.spring.boot_security.demo.init;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.furmish.spring.boot_security.demo.model.Role;
import ru.furmish.spring.boot_security.demo.model.User;
import ru.furmish.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final UserService userService;

    @PostConstruct
    public void initUsers() {
        final List<User> users = userService.getUsers();
        if (users.isEmpty()) {

            final var testUser = User
                    .builder()
                    .username("test")
                    .email("test@test.com")
                    .firstName("test")
                    .lastName("test")
                    .password("test")
                    .roles(List.of(new Role("ROLE_ADMIN")))
                    .build();
            final var adminUser = User
                    .builder()
                    .username("admin")
                    .email("admin@gmail.com")
                    .firstName("admin")
                    .lastName("admin")
                    .password("admin")
                    .roles(List.of(new Role("ROLE_ADMIN"), new Role("ROLE_USER")))
                    .build();
            final var user = User
                    .builder()
                    .username("user")
                    .email("user@gmail.com")
                    .firstName("user")
                    .lastName("user")
                    .password("user")
                    .roles(List.of(new Role("ROLE_USER")))
                    .build();

            userService.saveAllUsers(List.of(user, testUser, adminUser));
        }
    }
}
