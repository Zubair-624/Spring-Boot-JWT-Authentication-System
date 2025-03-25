package com.example.springbootsecurityjwtauthenticationandauthorization.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenRequestDTO  {

    @NotBlank(message = "Refresh token is required")
    private String refreshToken;
}