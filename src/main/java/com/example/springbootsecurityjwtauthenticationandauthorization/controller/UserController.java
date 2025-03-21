package com.example.springbootsecurityjwtauthenticationandauthorization.controller;

import com.example.springbootsecurityjwtauthenticationandauthorization.dto.UserRequestDTO;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // Get user by ID (Only ADMIN can access)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserByUserId(@PathVariable Long UserId){
        log.info("Fetching user with ID: {}", UserId);

        return ResponseEntity.ok(userService.getByUserId(UserId));
    }

    // Get all users (Only ADMIN can access)
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        log.info("Fetching all users");

        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Update user by ID
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long userId, @RequestBody UserRequestDTO userRequestDTO){
        log.info("Updating user with ID: {}", userId);

        return ResponseEntity.ok(userService.updateUserByUserId(userId, userRequestDTO));
    }

    // Delete user by ID (Only ADMIN can access)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable Long userId){
        log.info("Deleting user with ID: {}", userId);

        userService.deleteUserByUserId(userId)
        return ResponseEntity.ok("Deleted user with ID: " + userId);
    }










}


