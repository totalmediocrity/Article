package com.example.demo.controllers;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    public String registration(
        Model model,@ModelAttribute("user") User user ){
            return "registration";
        }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model){
        User userFromDb = userRepository.findByUsername(user.getUsername());

        Boolean haveErrors = false;

        if(userRepository.findByUsername(user.getUsername()) != null && !userRepository.findByUsername(user.getUsername()).getId().equals(user.getId())){
            model.addAttribute("messageUsername", "Ник уже занят. Добавьте креатива при создании");
            haveErrors = true;
        }


        if(bindingResult.hasErrors() || haveErrors){
            return "registration";
        }

        if(userFromDb != null)
        {
            model.addAttribute("message", "Пользователь с таким логином уже зарегистрирован.");

            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.ADMIN));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/login";
    }
}
