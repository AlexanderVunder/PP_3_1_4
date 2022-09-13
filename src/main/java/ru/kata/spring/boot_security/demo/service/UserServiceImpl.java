package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import java.util.List;


@Component
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    public final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Role> getAllRoles() {
       return roleRepository.findAll();
    }

    @Override
    @Transactional
    public void save(User user) {
        String pass = passwordEncoder.encode(user.getPassword());
        user.setPassword(pass);
        userRepository.save(user);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void update(int id, User updateUser) {
        User user = userRepository.findById(id).orElse(null);
        if (updateUser.getPassword().equals(user.getPassword())) {
            userRepository.save(updateUser);
        } else {
            String pass = passwordEncoder.encode(updateUser.getPassword());
            updateUser.setPassword(pass);
            userRepository.save(updateUser);
        }
    }

    @Override
    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }

}
