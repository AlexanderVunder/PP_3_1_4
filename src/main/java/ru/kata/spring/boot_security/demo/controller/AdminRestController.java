package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
@RestController
public class AdminRestController {

    private final UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/adminRest")
    public ResponseEntity<List<User>> printUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/adminRest/{id}")
    public ResponseEntity<User> user(@PathVariable int id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/adminRest")
    public ResponseEntity<User> newUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PatchMapping("/adminRest/{id}")
    public ResponseEntity<User> edit(@PathVariable int id, @RequestBody User user) {
        userService.update(id,user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/adminRest/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        userService.delete(id);
        return new ResponseEntity<>("delete successful", HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> findRoles() {
        return new ResponseEntity<>(userService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping("/viewUser")
    public ResponseEntity<User> showUser(Authentication auth) {
        return new ResponseEntity<>((User) auth.getPrincipal(), HttpStatus.OK);
    }
}
