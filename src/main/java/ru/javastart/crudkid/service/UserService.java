package ru.javastart.crudkid.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javastart.crudkid.model.Elective;
import ru.javastart.crudkid.model.Role;
import ru.javastart.crudkid.model.User;
import ru.javastart.crudkid.repository.ElectiveRepository;
import ru.javastart.crudkid.repository.RoleRepository;
import ru.javastart.crudkid.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ElectiveRepository electiveRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, ElectiveRepository electiveRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.electiveRepository = electiveRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        log.info("UserDetails for {}", username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getName(), new BCryptPasswordEncoder().encode(user.getPassword()), authorities);
    }

    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    public User findById(Long id) {
        return userRepository.getById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public Set<Elective> showElectives(Long id) {
        return userRepository.getById(id).getElectives();
    }

    public void addElective(Long userId, Long electiveId) {
        User user = userRepository.getById(userId);
        user.getElectives().add(electiveRepository.getById(electiveId));
        userRepository.save(user);
    }

    public void removeElective(Long userId, Long electiveId) {
        User user = userRepository.getById(userId);
        user.getElectives().remove(electiveRepository.getById(electiveId));
        userRepository.save(user);
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public void addRoleToUser(String userName, String roleName) {
        User user = userRepository.findByName(userName);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
    }


}
