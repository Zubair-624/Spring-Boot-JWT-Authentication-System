package com.example.springbootsecurityjwtauthenticationandauthorization.service;

import com.example.springbootsecurityjwtauthenticationandauthorization.model.BlacklistToken;
import com.example.springbootsecurityjwtauthenticationandauthorization.repository.BlacklistTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlacklistTokenService {

    private final BlacklistTokenRepository blacklistTokenRepository;

    public void blacklistToken(String token){
        BlacklistToken blacklistToken = new BlacklistToken(token);
        blacklistTokenRepository.save(blacklistToken);
    }

    public boolean isTokenBlacklisted(String token){
        return blacklistTokenRepository.findByToken(token).isPresent(); 
    }

}
