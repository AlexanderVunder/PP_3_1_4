package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
public class AdminController {

    public final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/admin")
    public String printUsers(ModelMap model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = user.getId();
        user = userService.findById(userId);
        model.addAttribute("roles", userService.getAllRoles());
        model.addAttribute("userNav", user);
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping(value = "/new")
    public String newUser(ModelMap model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = user.getId();
        user = userService.findById(userId);
        model.addAttribute("roles", userService.getAllRoles());
        model.addAttribute("user", new User());
        model.addAttribute("userNav", user);
        return "new";
    }
    @PostMapping(value = "/admin")
    public String saveNewUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(ModelMap model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.findById(id));
        return "edit";
    }

    @PostMapping("/admin/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id, @RequestParam(value = "role") String role) {
        user.setRoles(userService.findRoleByName(role));
        userService.update(id, user);
        return "redirect:/admin";
    }
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
