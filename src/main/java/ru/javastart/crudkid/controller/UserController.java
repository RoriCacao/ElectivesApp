package ru.javastart.crudkid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javastart.crudkid.model.User;
import ru.javastart.crudkid.service.ElectiveService;
import ru.javastart.crudkid.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private ElectiveService electiveService;

    @Autowired
    public UserController(UserService userService, ElectiveService electiveService) {
        this.userService = userService;
        this.electiveService = electiveService;
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
        model.addAttribute("electives", userService.showElectives(id));
        model.addAttribute("freeElectives", electiveService.findAllFreeTrainings(id));
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
    public String updateUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user-update";
    }

    @PostMapping("/{id}")
    public String update(User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }

    @PostMapping("/{id}/removeElective/{electiveId}")
    public String removeElective(@PathVariable("id") Long id, @PathVariable("electiveId") Long electiveId) {
        userService.removeElective(id, electiveId);
        return "redirect:/users/{id}";
    }

    @PostMapping("/{id}/addElective")
    public String addElective(@PathVariable("id") Long id, @RequestParam("electiveId") Long electiveId) {
        userService.addElective(id, electiveId);
        return "redirect:/users/{id}";
    }
}
