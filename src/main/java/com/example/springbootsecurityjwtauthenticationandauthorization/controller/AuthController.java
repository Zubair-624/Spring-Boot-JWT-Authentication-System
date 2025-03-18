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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    //REGISTER: User registration
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO request) {
        UserResponseDTO registeredUser = userService.registerUser(request);
        return ResponseEntity.ok(registeredUser);
    }

    //LOGIN: Authenticate user & return JWT tokens
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> authenticateUser(@RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authService.authenticateUser(request));
    }

    //REFRESH TOKEN: Get a new access token using refresh token
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDTO> refreshAccessToken(@RequestParam String refreshToken) {
        RefreshToken newToken = refreshTokenService.validateRefreshToken(refreshToken);
        String newAccessToken = jwtUtil.generateToken(newToken.getUser().getEmail());
        return ResponseEntity.ok(new AuthResponseDTO(newAccessToken, refreshToken));
    }
}
