package com.example.demo.controllers;

import com.example.demo.models.Post;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user-list";
    }

    @GetMapping("/{id}/edit")
    public String userEdit(@PathVariable(value = "id") long id, Model model) {
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }

    @PostMapping
    public String userSave(@RequestParam String username, @RequestParam(name = "roles[]", required = false) String[] roles,
                           @RequestParam("userId") User user) {
        user.setUsername(username);
        user.getRoles().clear();
        if (roles != null) {
            Arrays.stream(roles).forEach(r -> user.getRoles().add(Role.valueOf(r)));
        }
        userRepository.save(user);

        return "redirect:/admin";
    }

    @GetMapping("/{id}/remove")
    public String userRemove(@PathVariable(name = "id") Long id, Model model) {
        User current = userRepository.findById(id).orElseThrow();

        userRepository.delete(current);
        return "redirect:/admin";
    }
}