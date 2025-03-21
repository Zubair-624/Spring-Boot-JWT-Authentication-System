package com.example.springbootsecurityjwtauthenticationandauthorization.controller;

import com.example.springbootsecurityjwtauthenticationandauthorization.service.BlacklistTokenService;
import com.example.springbootsecurityjwtauthenticationandauthorization.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    private final BlacklistTokenService blacklistTokenService;

    // Blacklist a JWT token
    @PostMapping("/revoke")
    public ResponseEntity<String> revokeToken(@RequestParam String token){
        log.info("Revoking token: {}", token);

        blacklistTokenService.blacklistToken(token);

        return ResponseEntity.ok("Token revoked successfully");
    }






}
