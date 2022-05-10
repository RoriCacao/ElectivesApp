package ru.javastart.crudkid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javastart.crudkid.model.Elective;
import ru.javastart.crudkid.model.User;
import ru.javastart.crudkid.repository.ElectiveRepository;
import ru.javastart.crudkid.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ElectiveRepository electiveRepository;

    @Autowired
    public UserService(UserRepository userRepository, ElectiveRepository electiveRepository) {
        this.userRepository = userRepository;
        this.electiveRepository = electiveRepository;
    }


    public User findById(Long id) {
        return userRepository.getById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public Set<Elective> showElectives(Long id){
        return userRepository.getById(id).getElectives();
    }

    public void addElective(Long userId, Long electiveId){
        Elective elective = electiveRepository.getById(electiveId);
        User user = userRepository.getById(userId);
        Set<Elective> electives = user.getElectives();
        electives.add(elective);
    }

    public void removeElective(Long userId, Long electiveId){
        Elective elective = electiveRepository.getById(electiveId);
        userRepository.getById(userId).getElectives().remove(elective);
    }

}
