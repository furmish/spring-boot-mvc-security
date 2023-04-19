package ru.furmish.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.furmish.spring.boot_security.demo.model.User;
import ru.furmish.spring.boot_security.demo.dao.UserDao;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao dao;
    private final RoleService roleService;
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsers() {
        return dao.listUsers();
    }

    @Override
    public User getUserByEmail(String email) {
        return dao.getUserByEmail(email);
    }

    @Override
    public User getUserByUsername(String username) {
        return dao.getUserByUsername(username);
    }

    @Override
    public void addUser(User user) {
        encodePassword(user);
        setRolesForUser(user);
        dao.createUser(user);
    }

    @Override
    public void removeUser(User user) {
        dao.removeUser(user);
    }

    @Override
    public User getUserById(long id) {
        return dao.getUserById(id);
    }

    @Override
    public void updateUser(User user) {
        User userFromDb = dao.getUserById(user.getId());
        if (!userFromDb.getPassword().equals(user.getPassword())) {
            encodePassword(user);
        }
        setRolesForUser(user);
        dao.updateUser(user);
    }

    private void setRolesForUser(User user) {
        user.setRoles(
                user.getRoles()
                        .stream()
                        .map(role -> roleService.findByName(role.getName()))
                        .toList());
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }
}
