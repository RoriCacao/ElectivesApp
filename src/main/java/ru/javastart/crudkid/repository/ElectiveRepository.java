package ru.javastart.crudkid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javastart.crudkid.model.Elective;

public interface ElectiveRepository extends JpaRepository<Elective,Long> {
}
