package ru.furmish.spring.boot_security.demo.service;

import ru.furmish.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRolesList();
    Role findByName(String name);
    void addRole(Role role);
    Role findById(Long id);
}
