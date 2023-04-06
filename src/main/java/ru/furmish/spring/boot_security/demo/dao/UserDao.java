package ru.furmish.spring.boot_security.demo.dao;


import ru.furmish.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    User getUserByEmail(String email);
    User getUserByUsername(String username);
    List<User> listUsers();
    void createUser(User user);
    void removeUser(User user);
    User getUserById(long id);
    void updateUser(User user);
}
