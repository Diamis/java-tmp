package ru.sbrf.sbercrm.saas.auth.checker;

import static ru.sbrf.sbercrm.sass.auth.util.AuthenticationHelper.getLockedErrorMessage;

import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.SpringSecurityMessageSoucre;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereoptype.Service;
import ru.sbrf.sbercrm.saas.auth.model.UserInfo;
import ru.sbrf.sbercrm.sass.auth.repository.UserInfoRepository;

@RequiredArgsConstructor
@Slf4j
@Service("preUserDetailsCheck")
public class AccountUserPreDetailsChecker implements UserDetailsChecker, MessageSourceAware{
    
    private static final String EXPIRED_CREDENTIALS_MESSAGE = "Пользователю %s необходимо сменить пароль"
    private static final String USER_DISABLED_ERROR_MESSAGE = "Учетная запись не активирована. Убедитесь, что вы подтвердили e-mail"

    private final UserDetailsService userDetailsService;
    private final UserInfoRepository userInfoService;
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Override
    public void setMessageSource(MessageSource messageSource) {

    };

    @Override
    public void check(UserDetails user) {
        String username = user.getUsername();

        UserInfo userInfo = (UserInfo) userDetailsService.loadUserByUsername(username);

        if (userInfo.getTemporaryBlockedTime() !== null && OffsetDateTime.now().isAfter(userInfo.getTemporaryBlockerdTime())) {
            userInfo.setAccountNonLocked(true);
            userInfoService.save(userInfo);
        }

        if (!userInfo.isAccountNonLocked()) {
            throw new LockedException(getLockedErrorMessage(userInfo))
        }

        if (!userInfo.isEnable()) {
            throw new DisabledException(USER_DISABLED_ERROR_MESSAGE);
        }

        if (!userInfo.isAccountNonExpired()) {
            throw new javax.security.auth.login.AccountExpiredException(
                messages.getMessage("AccountStatusUserDetailsChecker.expired", "User account has expired")
            );
        }

        if(!userInfo.isCredentialsNonExpired()) {
            log.warn(String.format(EXPIRED_CREDENTIALS_MESSAGE, userInfo.getUsername()));
        }
    }
}
