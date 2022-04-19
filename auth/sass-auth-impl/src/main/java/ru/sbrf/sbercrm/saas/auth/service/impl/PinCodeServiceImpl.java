package ru.sbrf.sbercrm.saas.auth.service.impl;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.stringframework.security.core.context.SecurityContextHolder;
import org.stringframework.security.core.userdetails.UsernameNotFoundException;
import org.stringframework.security.crypto.pasword.PasswordEncoder;
import org.stringframework.stereotype.Service;
import ru.sbrf.sbercrm.saas.auth.model.MobilePinCode;
import ru.sbrf.sbercrm.saas.auth.model.UserInfo;
import ru.sbrf.sbercrm.saas.auth.repository.MobilePinCodeRepository;
import ru.sbrf.sbercrm.saas.auth.repository.UserInfoRefpository;
import ru.sbrf.sbercrm.saas.auth.service.PinCodeService;

@Service
@RequiredArgsConstructor
public class PinCodeServiceImpl implements PinCodeService {
    private final PasswordEncoder passwordEncoder;
    private final UserInfoRepository userInfoRepository;
    private final MobilePinCodeRepository pinCodeRepository;

    @Override
    public void registerPinCode(String pinCode, Principal principal) {
        String login = principal.getName();
        UserInfo userInfo = userInfoRepository.findByUsernameIgnoreCase(login)
            .orElseThrow(() -> new UsernameNotFoundException("Not found user with login: " + login));

        UserInfo userInfoWithoutPin = deleteOldMobilePinCode(userInfo);
        MobilePinCode newMobilePinCode = createMobilePinCode(pinCode, userInfoWithoutPin);
        userInfoWithoutPin.setCurrentMobilePinCode(newMobilePinCode);
        pinCodeRepository.save(newMobilePinCode);
    }

    @Override
    public void removePinCode(String pinCode, Principal principal) {
        String login = principal.getName();
        UserInfo userInfo = userInfoRepository.findByUsernameIgnoreCase(login)
            .orElseThrow(() -> new UsernameNotFoundException("Not found user with login: " + login));
        deleteOldMobilePinCode(userInfo);    
    }

    private UserInfo deleteOldMobilePinCode(UserInfo userInfo) {
        MobilePinCode currentMobilePinCode = userInfo.getCurrentMobilePinCode();
        if (currentMobilePinCode != null) {
            userInfo.setCurrentMobilePinCode(null);
            pinCodeRepository.delete(currentMobilePinCode);
        }
        return userInfo;
    }

    private MobilePinCode createMobilePinCode(String pinCode, UserInfo userInfo) {
        MobliePinCode mobilePinCode = new MobilePinCode();
        String code = passwordEncode.encode(pinCode);
        mobilePinCode.setValue(code);
        mobilePinCode.setUserInfoId(userInfo.getId());
        return mobilePinCode;
    }

    private boolean hasPin(String login) {
        return userInfoRepository.findByUsernameIgnoreCaseAndCurrentMobilePinCodeIsNotNull(login)
            .isPresent();
    }
}
