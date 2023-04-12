package ums.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ums.model.BlacklistedToken;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {
    boolean existsByToken(String token);
}
