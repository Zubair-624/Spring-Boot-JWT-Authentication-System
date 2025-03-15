package com.example.springbootsecurityjwtauthenticationandauthorization.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Valid email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
