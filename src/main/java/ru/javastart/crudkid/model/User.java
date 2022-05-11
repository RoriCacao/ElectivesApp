package ru.javastart.crudkid.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
public class User {

    public User() {
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String sex;
    @Column
    private int age;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "clients_electives",
            joinColumns = {@JoinColumn(name = "clients_id")},
            inverseJoinColumns = {@JoinColumn(name = "electives_id")})
    private Set<Elective> electives = new HashSet<>();

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


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<Elective> getElectives() {
        return electives;
    }

    public void setElectives(Set<Elective> electives) {
        this.electives = electives;
    }

   /* public void addElective(Elective elective) {
        this.electives.add(elective);
    }*/
}
