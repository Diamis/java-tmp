package ru.sbrf.sbercrm.saas.auth.service;

import ru.sbrf.sbercrm.saas.auth.dto.AuthorizationDto;
import ru.sbrf.sbercrm.saas.auth.dto.MobileTokenDto;

public class MobileAuthorizationService {
    MobileTokenDto login(AuthorizationDto authorizationDto);
}
