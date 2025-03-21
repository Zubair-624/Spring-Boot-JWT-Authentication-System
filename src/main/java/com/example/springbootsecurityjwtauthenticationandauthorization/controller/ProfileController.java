package com.example.springbootsecurityjwtauthenticationandauthorization.controller;

import com.example.springbootsecurityjwtauthenticationandauthorization.dto.UserRequestDTO;
import com.example.springbootsecurityjwtauthenticationandauthorization.dto.UserResponseDTO;
import com.example.springbootsecurityjwtauthenticationandauthorization.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    // Get logged-in user profile
    @GetMapping
    public ResponseEntity<UserResponseDTO> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Fetching profile for user: {}", userDetails.getUsername());

        return ResponseEntity.ok(userService.getByEmail(userDetails.getUsername()));
    }

    // Update profile (Only the logged-in user can update)
    @PutMapping
    public ResponseEntity<UserResponseDTO> updateUserProfile(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserRequestDTO userRequestDTO){
        log.info("Updating profile for user: {}", userDetails.getUsername());

        return ResponseEntity.ok(userService.updateUserByUserEmail(userDetails.getUsername(), userRequestDTO));
    }



}
