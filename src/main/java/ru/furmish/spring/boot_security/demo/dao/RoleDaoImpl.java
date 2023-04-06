package ru.furmish.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.furmish.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public RoleDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Role> setRoles() {
        return new ArrayList<>(entityManager.createQuery("from Role", Role.class).getResultList());
    }

    @Override
    public Role findByName(String name) {
        return entityManager.createQuery("from Role where name = ?1", Role.class).setParameter(1, name).getSingleResult();
    }

    @Override
    public Role findById(String id) {
        return entityManager.createQuery("from Role where id = ?1", Role.class).setParameter(1, id).getSingleResult();
    }

    @Override
    public void createRole(Role role) {
        entityManager.persist(role);
    }
}
