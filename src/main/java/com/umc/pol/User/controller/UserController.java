package com.umc.pol.User.controller;

import com.umc.pol.User.repository.User;
import com.umc.pol.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/name")
    public List<User> getName() {
        return userRepository.findAll();
    }
}
