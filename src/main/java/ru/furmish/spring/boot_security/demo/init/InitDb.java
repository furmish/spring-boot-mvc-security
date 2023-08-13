package ru.furmish.spring.boot_security.demo.init;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.furmish.spring.boot_security.demo.model.Role;
import ru.furmish.spring.boot_security.demo.service.RoleService;
import ru.furmish.spring.boot_security.demo.service.UserService;
import ru.furmish.spring.boot_security.demo.model.User;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final UserService userService;

    private final RoleService roleService;

    @PostConstruct
    public void postConstruct() {
        List<User> users = userService.getUsers();
        if (users.isEmpty()) {
            Role adminRole = new Role("ROLE_ADMIN");
            Role userRole = new Role("ROLE_USER");
            roleService.addRole(adminRole);
            roleService.addRole(userRole);

            List<Role> testRoles = new ArrayList<>();
            testRoles.add(adminRole);
            testRoles.add(userRole);
            List<Role> adminRoles = new ArrayList<>();
            adminRoles.add(adminRole);
            List<Role> userRoles = new ArrayList<>();
            userRoles.add(userRole);

            userService.addUser(new User("test", "test", "test", "test", "test@gmail.com", testRoles));
            userService.addUser(new User("admin", "admin", "admin", "admin", "admin@gmail.com", adminRoles));
            userService.addUser(new User("user", "user", "user", "user", "user@gmail.com", userRoles));
        }
    }
}
