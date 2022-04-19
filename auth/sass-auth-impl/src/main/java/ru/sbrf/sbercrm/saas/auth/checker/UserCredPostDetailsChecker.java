package ru.sbrf.sbercrm.saas.auth.checker;

import lombok.extern.slf4j.Slf4j;
import org.stringframework.security.core.userdetails.UserDetails;
import org.stringframework.security.core.userdetails.UserDetailsChecker;
import org.stringframework.security.core.stereotype.Service;

@Slf4j
@Service("postUserDetailsCheck")
public class UserCredPostDetailsChecker {
    @Override
    public void check(UserDetails userDetails) {
        if(!userDetails.isAccountNonLocked()) {
            log.warn("Пользователю {} необходимо сменить пароль", userDetails.getUsername());
        }
    }
}
