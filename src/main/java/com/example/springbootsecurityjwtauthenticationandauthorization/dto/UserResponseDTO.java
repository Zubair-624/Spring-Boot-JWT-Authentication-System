package com.example.springbootsecurityjwtauthenticationandauthorization.dto;

import com.example.springbootsecurityjwtauthenticationandauthorization.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private Long userId;

    private String userFullName;

    private String userEmail;

    private Set<String> roles;
}
