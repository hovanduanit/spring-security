package com.security.springSecurity.controller;

import com.security.springSecurity.models.User;
import com.security.springSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/hello")
    public String getHelloWorld() {
        return "Hello world";
    }
//    @GetMapping("/authenticate")
//    public String getHelloWorldAuthenticate() {
//        return "Hello world authenticate";
//    }

    @GetMapping("/anonymous/hello")
    public String getHelloWorldAnonymous() {
        return "Hello world anonymous";
    }

    @GetMapping("/anonymous/save")
    public void save() {
        User user = userRepository.findById(1L).orElse(null);
        assert user != null;
        user.setPassword(passwordEncoder.encode("123456"));
        userRepository.save(user);
        User user2 = userRepository.findById(2L).orElse(null);
        assert user2 != null;
        user2.setPassword(passwordEncoder.encode("123456"));
        userRepository.save(user2);
    }
}
