package ru.furmish.spring.boot_security.demo.service;


import ru.furmish.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUserByEmail(String email);
    User getUserByUsername(String username);
    void addUser(User user);
    void removeUser(User user);
    User getUserById(long id);
    void updateUser(User user);
}
