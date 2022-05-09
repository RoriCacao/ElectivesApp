package ru.javastart.crudkid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javastart.crudkid.model.Elective;
import ru.javastart.crudkid.service.ElectiveService;

import java.util.List;

@Controller
@RequestMapping("/electives")
public class ElectiveController {
    private ElectiveService electiveService;

    @Autowired
    public ElectiveController(ElectiveService electiveService) {
        this.electiveService = electiveService;
    }

    @GetMapping
    public String findAll(Model model){
        List<Elective> electives = electiveService.findAll();
        model.addAttribute("electives",electives);
        return "/elective-list";
    }
}
