package org.example.backend.controllers;


import jakarta.validation.Valid;
import org.example.backend.dtos.RegisterRequest;
import org.example.backend.dtos.UserDTO;
import org.example.backend.services.UserRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reg")
public class UserRegController {
    private final UserRegService userRegService;

    @Autowired
    public UserRegController(UserRegService userRegService){
        this.userRegService = userRegService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid RegisterRequest request){
        try {
            UserDTO userDTO = userRegService.registerUser(request);
            return ResponseEntity.ok(userDTO);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(new UserDTO());
        }
    }
}
