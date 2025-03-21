package com.example.springbootsecurityjwtauthenticationandauthorization.service;

import com.example.springbootsecurityjwtauthenticationandauthorization.model.RefreshToken;
import com.example.springbootsecurityjwtauthenticationandauthorization.model.User;
import com.example.springbootsecurityjwtauthenticationandauthorization.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    // Step 1: Create a new refresh token for a user
    public RefreshToken createRefreshToken(User user){
        log.info("Creating refresh token for user ID: {}", user.getUserId());

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString()) // Generate a secure random token
                .expiryDate(Instant.now().plusSeconds(7 * 24 * 60 * 60)) // Expire in 7 days
                .build();

        refreshToken = refreshTokenRepository.save(refreshToken);
        log.info("Refresh token created: {}", refreshToken.getToken());

        return refreshToken;

    }

    // Step 2: Validate a refresh token (Check expiration)
    public  RefreshToken validateRefreshToken(String token){
        log.info("Validating refresh token: {}", token);

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> {
                    log.error("Invalid refresh token: {}", token);
                    return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");

                });

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            log.error("Expired refresh token: {}", token);
            refreshTokenRepository.delete(refreshToken);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Expired refresh token");
        }

        log.info("Refresh token is valid: {}", token);

        return refreshToken;

    }

    // Step 3: Delete refresh token (When user logs out)
    public void deleteRefreshToken(String token){
        log.info("Deleting refresh token: {}", token);

        refreshTokenRepository.deleteByToken(token);
        log.info("Refresh token is deleted: {}", token);
    }



}
