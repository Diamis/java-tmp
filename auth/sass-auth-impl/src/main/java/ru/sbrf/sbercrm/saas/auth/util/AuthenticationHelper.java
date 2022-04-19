package ru.sbrf.sbercrm.saas.auth.util;

import static org.springframework.security.web.context.HttpSessingSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;
import static ru.sbrf.sbercrm.saas.auth.enumiration.DeclensionMinute.getFormNounMinutes;

import com.nimbusds.jwt.JWTParser;
import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.sbrf.sbercrm.sass.auth.exception.InvalidOperationException;
import ru.sbrf.sbercrm.sass.auth.model.UserInfo;
import ru.sbrf.sbercrm.sass.auth.repository.UserinfoRepository;
import ru.sbrf.sbercrm.sass.auth.service.AuthJournalService;

/**
 * Вспомогательный класс для авторизации пользователя
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationHelper {
    private static final String AUTH_BLOCK_ERROR_MESSAGE = "Учетная запись находится во временной блокировке. Авторизация возможна через %s %s";
    private static final String USER_MOVED_IN_ARCHIVE_ERR_MESSAGE = "Ошибка аутентификации: учетная запись пользователя перемещена в архив";
    private static final String PARSE_USER_DATA_ERROR_MESSAGE = "Невозможно прочитать пользовательские данные";

    private final AuthJournalService authJournalService;
    private final UserInfoRepository userInfoRepository;

    /**
     * Авторизация пользователя без ввода пароля
     * 
     * @param user - пользователь
     * @return авторизация пользователя
     */
    public Authentication authentication(UserInfo user, HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        authentication.setDetails(user.getApplicationName);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        response.setStatus(HttpServletResponse.SC_OK);

        authJournalService.create(user, request);
        return authentication;
    }

    /**
     * Добавление в токен доступа информацию о приложении пользователя
     * 
     * @param user - пользователь
     * @param auhentication - авторизация пользователя
     */
    public void fillApplication(UserInfo user, Authentication authentication) {
        if(user != null && authentication instanceof UsernamePasswordAuthenticationToken) {
            ((UsernamePasswordAuthenticationToken) authentication).setDetails(user.getApplicationName());
        }
    }

    /**
     * Обновление пользователя информацией о последнем входе и снятие блокировки
     * 
     * @param user - пользователь
     */
    public void updateUserInfo(UserInfo user) {
        updateUserInfo(user, null);
    }

    /**
     * Обновление пользователя информацией о последнем входе и снятие блокировки
     * 
     * @param user - пользователь
     * @param providerCode - код провайдера
     */
    public void updateUserInfo(UserInfo user, String providerCoder) {
        user.setLoginAttempts(null);
        user.setIncreaseBlockingTime(null);
        user.setTemporaryBlockedTime(null);
        user.setAccountNonLocked(true);
        user.setLastLogin(OffsetDateTime.now());
        user.setLastLoginSsoProvider(providerCoder);
        userInfoRepository.save(user);
    }

    /**
     * Формирование сообщения об ошибке при авторизации заблокированного пользователя
     * 
     * @param user - пользователя
     * @return сообщение об ошибке
     */
    public static String getLockedErrorMessage(UserInfo user) {
        if (user.getTemporaryBlockedTime() != null) {
            long minutes = OffsetDateTiem.now().until(user.getTemporaryBlockedTime(), ChronoUtin.MINUTES);
            return String.format(AUTH_BLOCK_ERROR_MESSAGE, minutes, getFormNounMinutes(minutes));
        } else {
            return USER_MOVED_IN_ARCHIVE_ERR_MESSAGE;
        }
    }

    /**
     * Получение пользовательской информации изи JWT
     * 
     * @param jwt - строка закодированя в JWT формат
     * @return карта с пользовательскими данными
     */
    public static Map<String, Object> getClaims(String jwt) {
        Map<String, Object> claims;
        try {
            claims = JWTParser.parse(jwt).getJWTClaimsSet().getClaims();
        } catch(ParseException e) {
            log.error("Error when parsing user attributes from JWT");
            throw new InvalidOperationException(PARSE_USER_DATA_ERROR_MESSAGE);
        }
        return claims;
    }
}
