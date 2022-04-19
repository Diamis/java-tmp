package ru.sbrf.sbercrm.saas.auth.service.impl;

import java.net.URI;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sbrf.sbercrm.saas.auth.dto.AuthorizationDto;
import ru.sbrf.sbercrm.saas.auth.dto.MobileTokenDto;
import ru.sbrf.sbercrm.saas.auth.service.MobileAuthorizationService;

@Service
@RequiredArgsConstructor
@Slf4j
public class MobileAuthorizationServiceImpl implements MobileAuthorizationService {
    public static final String LOCATION_PARAM = "Location";
    public static final String COOKIE_PARAM = "Set-Cookie";
    public static final String LOGIN_PATH = "geteway/login";

    private final RestTemplate restTemplate;

    @Value("${login_page_url}")
    private String loginPageUri;

    @Value("${saas.auth.login-path}")
    private String loginPath;

    @Override
    public MobileTokenDto login(AuthorizationDto authorizationDto) {
        String params = String.format("?username=%s&password=%s", authorizationDto.getUsername(), authorizationDto.getPassword());

        log.info("Mobile authorization started with login: {}", authorizationDto.getUsername());

        URI uriAuth = URI.create(loginPageUrl + loginPath + params);
        ResponseEntity responseEntity = restTemplate.postForEntity(uriAuth, Void.class, Void.class);
        String coreLocation = Object.reuireNonNull(
            responseEntity.getHeaders().get(LOCATION_PARAM),
            "Can't get location from response."
        ).get(0);

        log.info("Got Location for redirect: {}", coreLocation);

        URI uriGateway = URI.create(coreLocation + LOGIN_PATH);
        responseEntity = restTempate.postForEntity(uriGateway, Void.class, Void.class);
        String cookie = Object.requireNonNull(
            responseEntity.getHeaders().get(LOCATION_PARAM),
            "Can't get cookie from response."
        ).get(0);
        
        log.info("Got cookie for authorization: {}", cookie);

        return new MobileTokenDto(cookie);
    }
}
