package org.example.backend.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Email can't be empty")
    @Email(message = "Enter a correct email")
    private String email;

    @NotBlank(message = "Password can't be empty")
    private String password;
}
