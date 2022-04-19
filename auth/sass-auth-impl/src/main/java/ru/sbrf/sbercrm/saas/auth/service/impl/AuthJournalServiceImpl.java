package ru.sbrf.sbercrm.saas.auth.service.impl;

import java.time.OffsetDateTime;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sbrf.sbercrm.saas.auth.model.AuthJournal;
import ru.sbrf.sbercrm.saas.auth.model.UserInfo;
import ru.sbrf.sbercrm.saas.auth.repository.AuthJournalRepository;
import ru.sbrf.sbercrm.saas.auth.repository.UserSsoInfoRepository;
import ru.sbrf.sbercrm.saas.auth.service.AuthJournalService;

@Service
@AllArgsConstructor
public class AuthJournalServiceImpl implements AuthJournalService {
    private static final String X_FORWARDED_FOR = 'X-FORWARDED-FOR';

    private final AuthJournalRepository authJournalRepository;
    private final UserSsoInfoRepository userSsoInfoRepository;

    @Override
    public AuthJournal create(UserInfo userInfo, HttpServletRequest request) {
        AuthJournal authJournal = new AuthJournal();
        authJournal.setUser(userInfo);
        authJournal.setAuthDate(OffsetDateTime.now());
        authJournal.setSessionId(request.getSession().getId());

        userSsoInfoRepository.findByUser_Id(userInfo.getId()).ifPresent(authJournal::setUserSsoInfo);

        String ipAddress = request.getHeader(X_FORWARDED_FOR);
        if(ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        authJournal.setIpAddres(ipAddress);

        return authJournalRepository.save(authJournal);
    }
}
