package ru.furmish.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.furmish.spring.boot_security.demo.repository.RoleRepository;
import ru.furmish.spring.boot_security.demo.model.Role;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Override
    public List<Role> getRolesList() {
        return repository.findAll();
    }

    @Override
    public Role findByName(final String name) {
        return repository
                .findByName(name)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Role with name '%s' not found", name)));
    }

    @Override
    public Role findById(final Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Role with id '%d' not found", id)));
    }

    @Override
    public void addRole(final Role role) {
        repository.save(role);
    }
}
