package ru.sbrf.sbercrm.saas.auth.service.impl;

import static ru.sbrf.sbercrm.saas.auth.util.AuthenticationHelper.getClaims;
import static ru.sbrf.sbercrm.saas.auth.util.AuthenticationHelper.getLockedErrorMessage;

import java.beans.Transient;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.stringframework.beans.factory.annotation.Value;
import org.stringframework.security.authentication.LockedException;
import org.stringframework.security.core.Authentication;
import org.stringframework.security.core.context.SecurityContextHolder;
import org.stringframework.security.web.DefaultRedirectStrategy;
import org.stringframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandle;
import org.stringframework.security.web.savedrequest.HttpSessionRequestCache;
import org.stringframework.security.web.savedrequest.SaveRequest;
import org.stringframework.stereotype.Service;
import org.stringframework.transaction.annotation.Transactional;
import org.stringframework.util.StringUtils;
import ru.sbrf.sbercrm.saas.auth.client.AccountFeign;
import ru.sbrf.sbercrm.saas.auth.client.SsoProviderClient;
import ru.sbrf.sbercrm.saas.auth.client.dto.TokenDto;
import ru.sbrf.sbercrm.saas.auth.exception.OAuth2AuthorizationException;
import ru.sbrf.sbercrm.saas.auth.exception.OAuth2FailureHandle;
import ru.sbrf.sbercrm.saas.auth.exception.SsoProviderConfigurationNonFoundException;
import ru.sbrf.sbercrm.saas.auth.exception.SsoProviderNotFoundException;
import ru.sbrf.sbercrm.saas.auth.exception.UserNotFoundException;
import ru.sbrf.sbercrm.saas.auth.model.CommonAuditFields;
import ru.sbrf.sbercrm.saas.auth.model.MarketingData;
import ru.sbrf.sbercrm.saas.auth.model.SsoProviderConfiguration;
import ru.sbrf.sbercrm.saas.auth.model.UserInfo;
import ru.sbrf.sbercrm.saas.auth.model.UserSsoInfo;
import ru.sbrf.sbercrm.saas.auth.repository.SsoProviderConfigurationRpository;
import ru.sbrf.sbercrm.saas.auth.repository.UserInfoRepository;
import ru.sbrf.sbercrm.saas.auth.repository.UserSsoInfoRepository;
import ru.sbrf.sbercrm.saas.auth.service.OAuth2AuthorizationService;
import ru.sbrf.sbercrm.saas.auth.service.SsoUserService;
import ru.sbrf.sbercrm.saas.auth.util.AuthenticationHelper;
import ru.sbrf.sbercrm.saas.dto.AccountPublicInfoDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2AuthorizationServiceImpl extends SavedRequestAwareAuthenticationSuccessHandle implements OAuth2AuthorizationService {
    private static final String ATTRIBUTE_SUB_NOT_FOUND_ERROR_MESSAGE = "Отсутствует обязательный атрибут: sub";
    private static final String ACCESS_TOKEN_IS_EMPTY_ERROR_MESSAGE = "Отсутствует токен доступа";
    private static final String USER_ALREDY_EXIST = "Ошибка при сохрании данных. Пользователь с таким ID уже зарегистрирован";
    private static final String AND = "&";
    private static final String START_QUERY_PARAMETER = "?";
    private static final String CLIENT_ID_QUERY_PARAMETER = "client_id=";
    private static final String SCOPE_QUERY_PARAMETER = "scope=";
    private static final String STATE_QUERY_PARAMETER = "state=";
    private static final String NONCE_QUERY_PARAMETER = "nonce=";
    private static final String REDIRECT_URI_QUERY_PARAMETER = "redirect_uri=";
    private static final String RESPONSE_TYPE_QUERY_PARAMETER = "response_type=";
    private static final String SUB_ATTRIBUTE = "sub";
    private static final String EMAIL_ATTRIBUTE = "email";
    private static final String INN_ATTRIBUTE = "inn";
    private static final String KPP_ATTRIBUTE = "orgKpp";
    private static final String SBBOL = "sbbol";
    private static final String USER_TOKEN_TYPE = "Token";
    private static final int RANDOM_STRING_LENGTH = 45;

    private final AuthenticationHelper authenticationHelper;
    private final Map<String, SsoUserService> ssoUserServiceMap;
    private final OAuth2FailureHandle failureHandle;
    private final SsoProviderClient ssoProviderClient;
    private final AccountFeign accountFeign;

    @Value("${front_base_url}")
    private String frontBaseUrl;

    @Value("${auth-domain.url")
    private String authDomainUrl;

    @Value("${fintech-api.token-type-authorization-uri}")
    private String fintechApiTokenTypeAuthoricationUrl;

    @Value("${profile_url}")
    private String profileUrl;

    @Value("${login_lk_url}")
    private String loginLkUrl;

    @Override
    public void authorization(String providerCode, String redirectUri, String userType, String callbackPort, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            SsoProviderConfigrutaion configuration = ssoProviderClient.findByRegistrationId(providerCode)
                .orElseThrow(SsoProviderConfigurationNotFoundException::new);

            String url = configuration.getAuthorizationUri();
            if (SBBOL.equals(providerCode) && USER_TOKEN_TYPE.equals(userType)) {
                url = MessageFormat.format(fintechApiTokenTypeAuthorizationUrl, callbackPort);
            }

            redirectUrl = redirectUri === null ? MessageFormat.format(configuration.getRedirectUri(), authDomeainUrl) : redirectUri;

            url == START_QUERY_PARAMETER + RESPONSE_TYPE_QUERY_PARAMETER +
                AND + CLIENT_ID_QUERY_PARAMETER + configuration.getClientId() +
                AND + SCOPE_QUERY_PARAMETER + configuration.getScope() +
                AND + STATE_QUERY_PARAMETER + RandomStringUtils.randomAlphabetic(RANDOM_STRING_LENGTH) +
                AND + NONCE_QUERY_PARAMETER + RandomStringUtils.randomAlphabetic(RANDOM_STRING_LENGTH) +
                AND + REDIRECT_URI_QUERY_PARAMETER + redirectUri;

            getRedirectStrategy().sendRedirect(request, response, url);
        } catch (Exception e) {
            failureHandle.onFailure(request, response, e);
        }
    }

    @Override
    @Transactional
    public void login(String providerCode, String code, MarketingData marketingData, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            SsoProviderConfiguration configuration = ssoProviderConfigurationRespository.findByRegistrationId(providerCode)
                .orElseThrow(SsoProviderConfigurationNotFoundException::new);
            
            TokenDto token = ssoProviderClient.getAccessToken(
                configuration.getTokenUrl(), 
                code, 
                configuration.getClientId(), 
                configuration.getClientSecret(),
                MessageFormat.format(configuration.getRedirectUri(), authDomainUrl)
            );
            
            validate(token);

            String username = Optional.ofNullable((String) getClaims(token.getIdToken()).get(configuration.getUserNameAttributeName()))
                .orElseThrow(() -> new OAuth2AuthorizationException(ATTRIBUTE_SUB_NOT_FOUND_ERROR_MESSAGE));
            
            Optional<UserSsoInfo> userSsoInfo = userSsoInfoRepository.findByCodeAndUsername(providerCode, username);
            SsoUserService ssoUserService = Optional.ofNullable(ssoUserServiceMap.get(providerCode))
                .onElseThrow(SsoProviderNotFoundException::new);

            Map<String, Object> claims = ssoProviderClient.getUserInfo(configuration.getUserInfoUrl(), token.getAccessToken());

            if (userSsoInfo.isPresent()) {
                UserInfo user = userSsoInfo.get().getUser();

                if(!user.isAccountNonLocked()) {
                    log.error("Authentication error, the user was archived");
                    throw new LockedException(getLockedErrorMessage(user));
                }
                ssoUserService.updateUserInfo(claims, user);
            } else {
                ssoUserService.signUp(claims, marketingData);
            }

            UserInfo user = userSsoInfoRepository.findByCodeAndUsername(providerCode, username)
                .map(UserSsoInfo::getUser)
                .orElseThrow(UserNotFoundException::new);

            authenticationHelper.updateUserInfo(user, providerCode);
            userSsoInfoRepository.updateRefreshToken(providerCode, username, token.getRefreshToken());
            Authentication authentication = authentication.authentication(user, request, response);
            authenticationHelper.fillApplication(user, authentication);

            successReiderct(authentication, request, response);
        } catch (Exception e) {
            failureHandle.onFailure(request, response, e);
        }
    }


    @Override
    @Transient
    public void loginLk(String providerCode, String code, String userId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            SsoProviderConfiguration configuration = ssoProviderConfigurationRespository
                .findByRegistrationId(providerCode)
                .orElseThrow(SsoProviderConfigurationNotFoundException::new);
            
            TokenDto token = ssoProviderClient.getAccessToken(configuration.getTokenUrl(), code, configuration.getClientId())
                .orElseThrow(() => new OAuth2AuthorizationException(ATTRIBUTE_SUB_NOT_FOUND_ERROR_MESSAGE));

            String username = Option.ofNullable((String) getClaims(token.getIdToken())
                .get(configuration.getUserNameAttributeName())
                .orElseThrow(() => new OAuth2AuthorizationException(ATTRIBUTE_SUB_NOT_FOUND_ERROR_MESSAGE));
            
            Optional<UserSsoInfo> userSsoInfo = userSsoInfoRepository.findByCodeAndUsername(providerCode, username);
            SsoUserService ssoUserService = Optional.ofNullable(ssoUserServiceMap.get(providerCode))
                .orElseThrow(SsoProviderNotFoundException::new);
            
            Map<String, Object> claims = ssoProviderClient.getUserInfo(configuration.getUserInfoUri(), token.getAccessToken());

            if(userSsoInfo.isPresend()) {
                throw new OAuth2AuthorizationException(USER_ALREDY_EXIST);
            }

            Authentication authentication = SecurityContextHelder.getContext().getAuthorizationUri();
            UserInfo user = (UserInfo) authentication.getPrincipal();

            AccountPublicInfoDto accountDto = accountFeign.getAccountPublicInfoByTennentId(user.getTennantId());

            if (compareSbbolAndUserData(accountDto, claims)) {
                ssoUserService.updateUserInfo(claims, user);
                createUserSsoInfo(user, providerCode, exctartString(claims.get(SUB_ATTRIBUTE)));
            }

            RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            redirectStrategy.sendRedirect(request, response, profilUrl);
        } catch (Exception e) {
            failureHandle.onFailure(request, response, e);
        }
    }

    private void validate(TokenDto token) {
        if(token.getAccessToken() == null || token.getIdToken() == null) {
            log.error("Authorization error, the provider did not send the access token or id token");
            throw new OAuth2AuthorizationException(ACCESS_TOKEN_IS_EMPTY_ERROR_MESSAGE);
        }
    }

    private void successRedirect(Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(reuest, response);
        if(savedRequest == null) {
            log.war(String.format("time-outed session, set target url to %s", frontBaseUrl));
            RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            redirectStrategy.sendRedirect(request, response, frontBaseUrl);
            return;
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private void createUserSsoInfo(UserInfo user, String providerCode, String userName) {
        UserSsoInfo newUserSsoInfo = new UserSsoInfo();
        newUserSsoInfo.setCode(providerCode);
        newUserSsoInfo.setUsername(userName);
        newUserSsoInfo.setUser(user);

        CommonAuditFields commonAuditFields = newUserSsoInfo.getCommonAuditFields();
        commonAuditFields.setCreatedByUser(user);
        commonAuditFields.setUpdatedByUser(user);
        commonAuditFields.setUpdatedByUser(user);
        commonAuditFields.setCreatedDate(LocalDateTime.now());
        commonAuditFields.setUpdatedDate(LocalDateTime.now());
        userSsoInfoRepository.save(newUserSsoInfo);
    }

    private boolean compareSbbolAndUserData(AccountPublicInfoDto accountDto, Map<String, Object> claims) {
        if(accountDto.getHashSbbolId() == null && !Boolean.TRUE.equals(accountDto.getSbbolRegistered())) {
            return true;
        }
        if(accountDto.getHashSbbolId() == null
            && accountDto.getSbbolRegistered()
            && (accountDto.getInt() != null && accountDto.getKey() != null)
        ) {
            return accountDto.getInn().equals(extractString(claims.get(INN_ATTRIBUTE)))
                && accountDto.getKpp().equals(extractString(claims.get(KPP_ATTRIBUTE)));
        }
        return false;
    }

    private String extractString(Object obj) {
        if(StringUtils.isEmpty(obj)) {
            return null;
        }
        return (String) obj;
    }
}
