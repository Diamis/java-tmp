package ru.sbrf.sbercrm.saas.auth.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbrf.sbercrm.saas.auth.model.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Lomg> {
    /**
     * Получить Optional<User> по username
     * @param username username пользователя
     */
    Optional<UserInfo> findByUsernameIgnoreCase(String username);

    Optional<UserInfo> findByUsernameIgnoreCaseAndCurrentMobilePinCodeIsNotNull(String username);

    Optional<UserInfo> findById(UUID id);

    List<UserInfo> findAllByEnabledTrueAndLastLoginLessThanEqual(OffsetDataTime lastLogin);

    /**
     * Полуить Optional<User> по username
     * @param username пользователя
     * @param email пользователя
     */
    Optional<UserInfo> findByUsernameOrEmail(String username, String email);
}