
package com.example.springbootsecurityjwtauthenticationandauthorization.repository;

import com.example.springbootsecurityjwtauthenticationandauthorization.model.BlacklistToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BlacklistTokenRepository extends JpaRepository<BlacklistToken, Long> {

    Optional<BlacklistToken> findByToken(String token); // ✅ Find if a token is revoked
}
