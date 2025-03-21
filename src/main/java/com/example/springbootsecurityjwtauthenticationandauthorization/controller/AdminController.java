package com.example.springbootsecurityjwtauthenticationandauthorization.controller;

import com.example.springbootsecurityjwtauthenticationandauthorization.dto.UserResponseDTO;
import com.example.springbootsecurityjwtauthenticationandauthorization.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    // Get all users (Only ADMIN can access)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        log.info("Admin fetching all users");

        return ResponseEntity.ok(userService.getAllUsers());

    }

    // Delete user by ID (Only ADMIN can access)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        log.info("Admin deleting user with ID: {}", userId);

        userService.deleteUserByUserId(userId);
        return ResponseEntity.ok("User deleted successfully");
    }




}
