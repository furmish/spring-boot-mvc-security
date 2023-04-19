package ru.furmish.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.furmish.spring.boot_security.demo.dao.RoleDao;
import ru.furmish.spring.boot_security.demo.model.Role;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Override
    public List<Role> getRolesList() {
        return roleDao.setRoles();
    }

    @Override
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }

    @Override
    public Role findById(String id) {
        return roleDao.findById(id);
    }

    @Override
    public void addRole(Role role) {
        roleDao.createRole(role);
    }
}
