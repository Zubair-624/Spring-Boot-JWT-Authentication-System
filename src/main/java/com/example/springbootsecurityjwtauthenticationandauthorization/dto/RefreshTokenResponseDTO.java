package com.example.springbootsecurityjwtauthenticationandauthorization.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenResponseDTO {

    private String accessToken;

    private String refreshToken;
}
