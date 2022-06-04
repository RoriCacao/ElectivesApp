package ru.javastart.crudkid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javastart.crudkid.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
