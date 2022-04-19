package ru.sbrf.sbercrm.saas.auth.service;

import javax.servlet.http.HttpServletRequest;
import ru.sbrf.sbercrm.saas.auth.model.AuthJournal;
import ru.sbrf.sbercrm.saas.auth.model.UserInfo;

public class AuthJournalService {
    AuthJournal create(Userinfo userInfo, HttpServletRequest request);
}
