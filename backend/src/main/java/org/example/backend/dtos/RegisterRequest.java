package org.example.backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 1, max = 50, message = "Username must contain from 1 to 50 characters")
    private String username;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Enter a correct email")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 4, message = "Password must contain at least 4 characters")
    private String password;

}
