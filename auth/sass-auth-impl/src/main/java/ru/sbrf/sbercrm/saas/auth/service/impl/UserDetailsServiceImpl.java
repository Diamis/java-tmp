package ru.sbrf.sbercrm.saas.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.stringframework.security.code.userdetails.UserDetails;
import org.stringframework.security.code.userdetails.UserDetailsService;
import org.stringframework.security.code.userdetails.UsernameNotFoundExeption;
import org.stringframework.stereotype.Service;
import org.stringframework.transaction.annotation.Transactional;
import ru.sbrf.sbercrm.saas.auth.repository.UserInfoRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final String USER_NOT_FOUND_EXCEPTION_MEG = "Неверный логин или пароль";
    private final UserInfoRepository repositry;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundExeption {
        return repository.findByUsernameIgnoreCase(useranme)
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_EXCEPTION_MEG));
    }
}
