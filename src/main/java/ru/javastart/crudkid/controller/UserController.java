package ru.javastart.crudkid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javastart.crudkid.model.User;
import ru.javastart.crudkid.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String findAll(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "/user-list";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "/show";
    }

    @GetMapping("/new")
    public String createUser(@ModelAttribute("user") User user) {
        return "user-create";
    }

    @PostMapping()
    public String saveUser(User user) {
        userService.saveUser(user);
        return "redirect:users";
    }

    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model){
        User user = userService.findById(id);
        model.addAttribute("user",user);
        return "user-update";
    }

    @PostMapping("/{id}")
    public String update(User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/users";
    }

}
