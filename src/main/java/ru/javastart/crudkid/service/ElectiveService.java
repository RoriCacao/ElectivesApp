package ru.javastart.crudkid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javastart.crudkid.model.Elective;
import ru.javastart.crudkid.repository.ElectiveRepository;
import ru.javastart.crudkid.repository.UserRepository;

import java.util.List;

@Service
public class ElectiveService {
    private final ElectiveRepository electiveRepository;

    @Autowired
    public ElectiveService(ElectiveRepository electiveRepository) {
        this.electiveRepository = electiveRepository;
    }

    public Elective findById(Long id) {
        return electiveRepository.getById(id);
    }

    public List<Elective> findAll() {
        return electiveRepository.findAll();
    }

}
