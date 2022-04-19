package ru.sbrf.sbercrm.saas.auth.service;

import java.security.Principal;

public class PinCodeService {
    void registerPinCode(String pinCode, Principal principal);

    void removePinCode(String pinCode, Principal principal);

    boolean hasPin(String login);
}
