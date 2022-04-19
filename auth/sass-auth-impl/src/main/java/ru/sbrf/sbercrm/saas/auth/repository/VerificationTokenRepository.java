package ru.sbrf.sbercrm.saas.auth.repository;

import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbrf.sbercrm.saas.auth.model.TokenType;
import ru.sbrf.sbercrm.saas.auth.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Lomg> {
    Optional<VerificationToken> findByHashAndType(UUID hash, TokenType type);
}