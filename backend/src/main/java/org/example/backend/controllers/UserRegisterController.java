package org.example.backend.controllers;


import jakarta.validation.Valid;
import org.example.backend.dtos.RegDTO;
import org.example.backend.model.User;
import org.example.backend.services.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reg")
public class UserRegisterController {
    private final UserRegisterService userRegisterService;

    @Autowired
    public UserRegisterController(UserRegisterService userRegisterService){
        this.userRegisterService = userRegisterService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegDTO request){
        try {
            User user = userRegisterService.registerUser(request);
            return ResponseEntity.ok("User registered:" + user.getUsername());
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
