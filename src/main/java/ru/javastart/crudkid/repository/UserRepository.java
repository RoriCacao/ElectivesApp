package ru.javastart.crudkid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javastart.crudkid.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
