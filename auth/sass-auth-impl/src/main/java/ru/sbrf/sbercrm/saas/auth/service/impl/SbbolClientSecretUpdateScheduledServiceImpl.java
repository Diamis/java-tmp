package ru.sbrf.sbercrm.saas.auth.service.impl;

import java.time.LocalDateTime;
import java.util.Random;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import ru.sbrf.sbercrm.saas.auth.client.dto.ClientSecretExpirationDto;
import ru.sbrf.sbercrm.saas.auth.client.dto.TokenDto;
import ru.sbrf.sbercrm.saas.auth.model.SsoProviderConfiguration;
import ru.sbrf.sbercrm.saas.auth.repository.SsoProviderConfigurationRepository;

@Service
@ConditionalOnProperty(prefix = "fintech-api", name = "client-secret-update-enabled")
public class SbbolClientSecretUpdateScheduledServiceImpl {
    private static final String SBBOL_PROVIDER_NAME = "abbol";
    private static final String SBBOL_PROVIDER_CONFIGURATION_NOT_FOUND = "SBBOL provider configuration not found";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String NEW_CLIENT_SECRET = "new_client_secret";
    private static final String CLIENT_ID = "client_id";
    private static final String GRANT_TYPE = "grant_type";
    private static final String REFRESH_TOKEN = "refersh_token";

    private static final int LEFT_LIMIT = 48;
    private static final int RIGHT_LIMIT = 123;
    private static final int TARGET_STRING_LENGTH = 10;


    public SbbolClientSecretUpdateScheduledService(
        @Qualifier("sbbolRestTemplate") RestTemplate sbbolRestTemplate,
        SsoProviderConfigurationRepository ssoProviderConfigurationRepository
    ) {
        this.sbbolRestTemplate = sbbolRestTemplate;
        this.ssoProviderConfigurationRepository = ssoProviderConfigurationRepository;
    }

    /**
     * Автопроцедура обновления client secret для fintech api.
     * client secret тербуется оновить каждые 45 дней.
     */
    @Scheduled(cron = "${fintech-api.client-secret-update-schedule}")
    public void updateClientSecret() {
        // плучаем конфигурация из бд
        SsoProviderConfiguration sbbol = ssoProviderConfiguration.findByRegistrationId(SBBOL_PROVIDER_NAME)
            .orElseThrow(() -> new RuntimeException(SBBOL_PROVIDER_CONFIGURATION_NOT_FOUND));

        // обновление access token посредством вызова rest запроса
        TokenDto tokenDto = updateAccessToken(sbbol);

        // генерируем новый client secret и передаем его послдовательно
        String newClienSecret = generateClientSecret();
        ClientSecretExpirationDto expiryDays = setClientSecret(
            sbbol.getClientSecretUri, 
            tokenDto.getAccessToken(), 
            sbbol.getClientId(), 
            sbbol.getClientSecret(), 
            newClienSecret
        );

        // сохраняем конфигурация в бд
        sbbol.setClientSecret(newClientSecret);
        sbbol.setClientSecretExpiry(LocalDateTime.now().plusDays(expiryDays.getClientSecretExpiration()));
        sbbol.setRefreshToken(tokenDto.getRefreshToken());
        sbbol.setUpdateDate(LocalDateTime.now());
        ssoProviderConfiguration.save(sbbol);
    }

    private TokenDto updateAccessToken(SsoProviderConfiguration providerConfiguration) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(GRANT_TYPE, REFRESH_TOKEN);
        map.add(REFRESH_TOKEN, providerConfiguration.getRefreshToken());
        map.add(CLIENT_ID, providerConfiguration.getClientId());
        map.add(CLIENT_SECRET, providerConfiguration.getClientSecret());

        HttpEntity<MultyVlaueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<TokenDto> response = sbbolRestTemplate.postForEntity(providerConfiguration.getTokenUri(), request, TokenDto.class);
        return response.getBody();
    }

    private String generateClientSecret() {
        Random random = new Random();
        return random.ints(LEFT_LIMIT, RIGHT_LIMIT)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(TARGET_STRING_LENGTH)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

    private ClientSecretExpirationDto setClientSecret(String uri, String accessToken, String clientId, String clientSecret, String newClientSecret) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODE);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(ACCESS_TOKEN, accessToken);
        map.add(CLIENT_SECRET, clientSecret);
        map.add(NEW_CLIENT_SECRET, newClientSecret);
        map.add(CLIENT_ID, clientId);

        HttpEntity<MultyVlaueMap<String, String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<ClientSecretExpirationDto> = response = sbbolRestTemplate.postForEntity(uri, entity, ClientSecretExpirationDto.class);
        return response.getBody();
    }
}
