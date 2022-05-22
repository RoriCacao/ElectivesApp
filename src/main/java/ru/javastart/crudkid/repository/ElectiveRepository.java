package ru.javastart.crudkid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.javastart.crudkid.model.Elective;

import java.util.List;

public interface ElectiveRepository extends JpaRepository<Elective,Long> {

    // @Query("SELECT COUNT(u.id) from User u inner join u.electives e where e.id=5" :electiveId) Рабочий код
    @Query("SELECT COUNT(u.id) from User u inner join u.electives e where e.id= :electiveId") //Тут HQL
    Long aa (@Param("electiveId") Long electiveId);

}

