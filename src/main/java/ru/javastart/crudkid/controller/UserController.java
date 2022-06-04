package ru.javastart.crudkid.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javastart.crudkid.model.Role;
import ru.javastart.crudkid.model.User;
import ru.javastart.crudkid.service.ElectiveService;
import ru.javastart.crudkid.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
        model.addAttribute("freeElectives", this.electiveService.findAllFreeTrainings(id));
        model.addAttribute("electiveService", electiveService);
        model.addAttribute("electivesCount", electiveService.findElectivesCount(id)); //Тут HQL
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

    @PostMapping("/{id}/removeElective")
    public String removeElective(@PathVariable("id") Long id, @RequestParam("electiveId") Long electiveId) {
        userService.removeElective(id, electiveId);
        return "redirect:/users/{id}";
    }

    @PostMapping("/{id}/addElective")
    public String addElective(@PathVariable("id") Long id, @RequestParam("electiveId") Long electiveId) {
        userService.addElective(id, electiveId);
        return "redirect:/users/{id}";
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.findByName(username);

                String access_token = JWT.create()
                        .withSubject(user.getName())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> token = new HashMap<>();
                token.put("access_token", access_token);
                token.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), token);
            } catch (Exception e) {

                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
