package org.example.backend.services;

import org.example.backend.dtos.UserDTO;
import org.example.backend.dtos.UserUpdateDTO;
import org.example.backend.model.User;
import org.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public UserDTO getCurrentUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        return UserDTO.fromEntity(user);  // Используем статический метод fromEntity
    }


    public Optional<UserDTO> updateUser (Long id, UserUpdateDTO userUpdateDTO, String currentUsername, boolean isAdmin) {
        return userRepository.findById(id).map(user -> {
            if(!isAdmin && !user.getUsername().equals(currentUsername)){
                throw new AccessDeniedException("You can only update your own profile!");
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
                throw new AccessDeniedException("You can only delete your own profile!");
            }

            userRepository.delete(user);
            return true;
        }).isPresent();
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole().name());
    }
}


