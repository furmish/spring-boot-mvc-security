package ru.furmish.spring.boot_security.demo.dao;

import ru.furmish.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleDao {
    List<Role> setRoles();
    Role findByName(String name);
    Role findById(String id);
    void createRole(Role role);
}
