package ru.sbrf.sbercrm.saas.auth.repository;

import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.sbrf.sbercrm.saas.auth.model.UserSsoInfo;

public interface UserSsoInfoRepository extends JpaRepository<UserSsoInfo, Lomg> {
    Optional<UserSsoInfo> findByCodeAndUsername(String code, String username);

    Optional<UserSsoInfo> findByUserId(UUID userId);

    @Modifying
    @Query("update UserSsoInfo usi set usi.refreshToken = refreshToken where usi.code = :code add usi.username = :username")
    void updateRefreshToken(
        @Param("code") String code, 
        @Param("username") String username,
        @Param("refreshToken") String refreshToken
    );
}