package org.example.backend.services;

import org.example.backend.dtos.UserDTO;
import org.example.backend.dtos.UserUpdateDTO;
import org.example.backend.model.User;
import org.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService (UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserDTO> getUserById (Long id) {
        return userRepository.findById(id).map(this::convertToDTO);
    }

    public Optional<UserDTO> updateUser (Long id, UserUpdateDTO userUpdateDTO, String currentUsername, boolean isAdmin) {
        return userRepository.findById(id).map(user -> {
            if(!isAdmin && !user.getUsername().equals(currentUsername)){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only update your own profile!");
            }

            user.setUsername(userUpdateDTO.getUsername());
            user.setEmail(userUpdateDTO.getEmail());
            if(userUpdateDTO.getPassword() != null && !userUpdateDTO.getPassword().isEmpty()){
                user.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
            }
            return convertToDTO(userRepository.save(user));
        });
    }

    public boolean deleteUser(Long id, String currentUsername, boolean isAdmin) {
        return userRepository.findById(id).map(user -> {
            if (!isAdmin && !user.getUsername().equals(currentUsername)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only delete your own profile!");
            }

            userRepository.delete(user);
            return true;
        }).orElse(false);
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }
}


