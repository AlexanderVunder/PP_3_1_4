package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<Role> getAllRoles();
    List<User> getAllUsers();

    void save(User user);

    User findById(int id);

    void update(int id, User updateUser);

    void delete(int id);

}
