package ru.furmish.spring.boot_security.demo.service;

import ru.furmish.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRolesList();
    Role findByName(String name);
    Role findById(String id);
    void addRole(Role role);
}
