package com.example.springbootsecurityjwtauthenticationandauthorization.controller;

import com.example.springbootsecurityjwtauthenticationandauthorization.dto.AuthRequestDTO;
import com.example.springbootsecurityjwtauthenticationandauthorization.dto.AuthResponseDTO;
import com.example.springbootsecurityjwtauthenticationandauthorization.dto.UserRequestDTO;
import com.example.springbootsecurityjwtauthenticationandauthorization.dto.UserResponseDTO;
import com.example.springbootsecurityjwtauthenticationandauthorization.model.RefreshToken;
import com.example.springbootsecurityjwtauthenticationandauthorization.service.AuthService;
import com.example.springbootsecurityjwtauthenticationandauthorization.service.RefreshTokenService;
import com.example.springbootsecurityjwtauthenticationandauthorization.service.UserService;
import com.example.springbootsecurityjwtauthenticationandauthorization.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    private final RefreshTokenService refreshTokenService;

    // User Registration API
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userRequestDTO){
        log.info("Registering user: {}", userRequestDTO.getEmail());

        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);

        return ResponseEntity.ok(userResponseDTO);
    }

    // Login API
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@RequestBody AuthRequestDTO authRequestDTO){
        log.info("User login attempt: {}", authRequestDTO.getEmail());

        AuthResponseDTO  authResponse = authService.authenticateUser(authRequestDTO);

        return ResponseEntity.ok(authResponse);

    }

    // Refresh Token API
    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshToken> refreshToken(@RequestParam String refreshToken) {
        log.info("Refreshing access token using refresh token: {}", refreshToken);

        return ResponseEntity.ok(refreshTokenService.validateRefreshToken(refreshToken));
    }



}



