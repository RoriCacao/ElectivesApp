package ru.javastart.crudkid.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "electives")
public class Elective {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String teacherName;
    @Column
    private int minAge;
    @Column
    private int maxNumberOfPeople;

    public Elective() {
    }

    public Elective(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "clients_electives",
            joinColumns = {@JoinColumn(name = "electives_id")},
            inverseJoinColumns = {@JoinColumn(name = "clients_id")})
    private Set<User> users = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxNumberOfPeople() {
        return maxNumberOfPeople;
    }

    public void setMaxNumberOfPeople(int maxNumberOfPeople) {
        this.maxNumberOfPeople = maxNumberOfPeople;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}
