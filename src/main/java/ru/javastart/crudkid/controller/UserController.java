package ru.javastart.crudkid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.javastart.crudkid.model.User;
import ru.javastart.crudkid.service.UserService;

import java.util.List;

@Controller
public class UserController {
    private UserService userService;

    @GetMapping("/users")
    public String findAll(){
        List<User> users = userService.findAll();

        return "";
    }

}
