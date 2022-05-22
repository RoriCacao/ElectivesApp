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
public class ElectiveService {
    private final ElectiveRepository electiveRepository;
    private final UserRepository userRepository;


    @Autowired
    public ElectiveService(ElectiveRepository electiveRepository, UserRepository userRepository) {
        this.electiveRepository = electiveRepository;
        this.userRepository = userRepository;
    }

    public Elective findById(Long id) {
        return electiveRepository.getById(id);
    }

    public List<Elective> findAll() {
        return electiveRepository.findAll();
    }

    public List<Elective> findAllFreeTrainings(Long id) {
        List<Elective> electives = findAll();
        User user = userRepository.getById(id);
        Set<Elective> usersElectives = user.getElectives();

        for (Elective e : usersElectives
        ) {
            if (electives.contains(e))
                electives.remove(e);
        }


        return electives;
    }

    public Long findElectivesCount(Long id) {  //Тут HQL
        return electiveRepository.aa(id);
    }


}
