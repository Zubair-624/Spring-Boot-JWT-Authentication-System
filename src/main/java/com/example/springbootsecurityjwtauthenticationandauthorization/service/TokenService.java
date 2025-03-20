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

    public List<Token> findByToken(String token){
        return tokenRepository.findTokenByUserId(token);
    }

    public void revokeToken(String token){
        tokenRepository.findTokenByUserId(token).ifPresent(tokenRepository::delete);
    }


}
