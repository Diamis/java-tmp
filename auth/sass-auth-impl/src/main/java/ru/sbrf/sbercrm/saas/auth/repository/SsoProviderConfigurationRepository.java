package ru.sbrf.sbercrm.saas.auth.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbrf.sbercrm.saas.auth.model.SsoProviderConfiguration;

public class SsoProviderConfigurationRepository extends JpaRepository<SsoProviderConfiguration, Long> {
    Optional<SsoProviderConfiguration> findByRegistrationId(String code);
}
