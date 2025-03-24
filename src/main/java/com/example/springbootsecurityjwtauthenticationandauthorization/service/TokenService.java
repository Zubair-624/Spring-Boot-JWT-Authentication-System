package com.example.springbootsecurityjwtauthenticationandauthorization.service;

import com.example.springbootsecurityjwtauthenticationandauthorization.model.Token;
import com.example.springbootsecurityjwtauthenticationandauthorization.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public Token saveToken(Token token){
        return tokenRepository.save(token);
    }

    public List<Token> findTokensByUserId(Long userId) { //
        return tokenRepository.findByUser_UserId(userId);
    }

    public void revokeToken(Long userId) {
        List<Token> tokens = tokenRepository.findByUser_UserId(userId);
        tokens.forEach(tokenRepository::delete);
    }



}
