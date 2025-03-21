package com.example.springbootsecurityjwtauthenticationandauthorization.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/password")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    // Change password (Only the logged-in user can change their password)
    @PutMapping("/change")
    public ResponseEntity<String> changePassword(@AuthenticationPrincipal UserDetails userDetails, @RequestMapping PasswordChangeDTO passwordChangeDTO){
        log.info("User: {} is changing password", userDetails.getUsername());

        passwordService.changePassword(userDetails.getUsername(), passwordChangeDTO);
        return ResponseEntity.ok("Password changed successfully");
    }

    // Reset password (Admin or email-based reset feature)
    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam String userEmail){
        log.info("Password reset request for email: {}", userEmail);

        passwordService.resetPassword(userEmail);
        return ResponseEntity.ok("Password reset instruction sent.")
    }



}
