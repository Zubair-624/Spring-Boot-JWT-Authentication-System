package com.example.springbootsecurityjwtauthenticationandauthorization.repository;

import com.example.springbootsecurityjwtauthenticationandauthorization.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, String> {

    List<Token> findTokenByUserId(String userId);


}
