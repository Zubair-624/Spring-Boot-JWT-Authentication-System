package com.example.springbootsecurityjwtauthenticationandauthorization.repository;

import com.example.springbootsecurityjwtauthenticationandauthorization.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findByUser_UserId(Long userId);


}
