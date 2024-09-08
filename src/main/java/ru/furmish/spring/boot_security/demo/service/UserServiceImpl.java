package ru.furmish.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.furmish.spring.boot_security.demo.model.User;
import ru.furmish.spring.boot_security.demo.repository.UserRepository;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsers() {
        return repository.findAll().stream()
                         .sorted(Comparator.comparing(User::getId)).toList();
    }

    @Override
    public User getUserByEmail(final String email) {
        return repository.findByEmail(email)
                         .orElseThrow(() -> new RuntimeException(
                "Пользователь с email - %s не найден!".formatted(email)));
    }

    @Override
    public User getUserByUsername(final String username) {
        return repository.findByUsername(username)
                         .orElseThrow(() -> new RuntimeException(
                "Пользователь с username = %s не найден".formatted(username)));
    }

    @Override
    public void addUser(final User user) {
        encodeUserPassword(user);
        repository.save(user);
    }

    private void encodeUserPassword(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    @Override
    public void removeUser(final User user) {
        repository.delete(user);
    }

    @Override
    public User getUserById(final long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(
                "Пользователь с id = %d не найден!".formatted(id)));
    }

    @Override
    public void updateUser(final User user) {
        final User userFromDb = repository
                .findById(user.getId())
                .orElseThrow(() -> new RuntimeException(
                        String.format("Пользователь с id = %d не найден!", user.getId())));

        if (!userFromDb.getPassword().equals(user.getPassword())) {
            userFromDb.setPassword(user.getPassword());
            encodeUserPassword(userFromDb);
        }
        userFromDb.setRoles(user.getRoles());

        repository.save(user);
    }

    @Override
    public void saveAllUsers(final List<User> users) {
        users.forEach(this::encodeUserPassword);
        repository.saveAll(users);
    }
}
