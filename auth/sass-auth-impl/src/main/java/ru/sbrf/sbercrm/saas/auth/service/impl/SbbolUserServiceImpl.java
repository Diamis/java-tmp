package ru.sbrf.sbercrm.saas.auth.service.impl;

import feing.FeignException;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.stringframework.stereotype.Service;
import org.stringframework.util.StringUtils;
import ru.sbrf.sbercrm.saas.auth.client.ApplicationFeign;
import ru.sbrf.sbercrm.saas.auth.client.AuthorizationInfoGatewqy;
import ru.sbrf.sbercrm.saas.auth.client.SignUpGateway;
import ru.sbrf.sbercrm.saas.auth.exception.ApplicationNotFoundException;
import ru.sbrf.sbercrm.saas.auth.exception.ManagementFeignException;
import ru.sbrf.sbercrm.saas.auth.exception.ValidationException;
import ru.sbrf.sbercrm.saas.auth.model.MarketingData;
import ru.sbrf.sbercrm.saas.auth.model.UserInfo;
import ru.sbrf.sbercrm.saas.auth.repository.UserInfoRepositry;
import ru.sbrf.sbercrm.saas.auth.service.SsoUserService;
import ru.sbrf.sbercrm.saas.dto.AccountDto;
import ru.sbrf.sbercrm.saas.dto.ApplicationDto;
import ru.sbrf.sbercrm.saas.dto.AuthorizationInfoDto;
import ru.sbrf.sbercrm.saas.dto.SingUpRqDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class SbbolUserServiceImpl implements SsoUserService {
    private static final String LAST_NAME_IS_EMPTY_ERROR_MESSAGE = "";
    private static final String FIRST_NAME_IS_EMPTY_ERROR_MESSAGE = "";
    private static final String REQUEST_APPLICATION_ERROR_MESSAGE = "";
    private static final String SUB_ATTRIBUTE = "";
    private static final String NAME_SEPARATOR = "";
    private static final String NAME_ATTRIBUTE = "";
    private static final String INN_ATTRIBUTE = "";
    private static final String KPP_ATTRIBUTE = "";
    private static final String OGRN_ATTRIBUTE = "";
    private static final String ORGANICATION_NAME_ATTRIBUTE = "";
    private static final String JURIDICAL_ADDRESS_ATTRIBUTE = "";
    private static final String HASH_SBBDL_ID_ATTRIBUTE = "";
    private static final String ACTUAL_ADDRESS_ATTRIBUTE = "";
    private static final String EMAIL_ATTRIBUTE = "";
    private static final String LAW_FORM_ATTRIBUTE = "";
    private static final String POSITION_ATTRIBUTE = "";
    private static final String PHONE_ATTRIBUTE = "";
    private static final String INDIVIDUAL_EXECUTIVE_AGENCY_ATTRIBUTE = "";
    private static final String USER_SIGNATURE_TYPE_ATTRIBUTE = "";
    private static final String wHOLESALE_APPLICATION_NAME = "";
    private static final String REGEX_NON_DIGITS = "";
    private static final String EMPTY_STRIGN = "";

    private static final Long SBBOL_TRUE_VALUE = 1L;
    private static final int LAST_NAME_INDEX = 0;
    private static final int FIRST_NAME_INDEX = 1;
    private static final int MIDDLE_NAME_INDEX = 2;

    private static final SignUpGateway signUpGateway;
    private static final ApplicationFeign applicationFeign;
    private static final UserInfoRepositry userInfoRepositry;
    private static final AuthorizationInfoGatewqy authorizationInfoGatewqy;

    @Override
    public void signUp(Map<String, Object> claims, MarketingData marketingData) throws IOException {
        signUpGateway.signup(makeSignUpRqDto(claims, marketingData));
    }

    @Override
    public void updateUserInfo(Map<String, Object> claims, Userinf userInfo) throws IOException {
        authorizationInfoGatewqy.updateUserInfo(makeAuthorizationInfoDto(claims, userInfo));
    }

    private AuthorizationInfoDto makeAuthorizationInfoDto(Map<String, Object> claims, UserInfo userInfo) {
        String[] namesArr = splitName(claims.get(NAME_ATTRIBUTE));
        return AuthorizationInfoDto.builder()
            .firstName(namesArr[FIRST_NAME_INDEX])
            .lastName(namesArr[LAST_NAME_INDEX])
            .middleName(extractMiddleName(MIDDLE_NAME_INDEX))
            .position(extractString(claims.get(POSITION_ATTRIBUTE)))
            .account(makeAccountDto(claims))
            .individualExecutiveAgency(extractBoolean(claims.get(INDIVIDUAL_EXECUTIVE_AGENCY_ATTRIBUTE))
            .userSignatureType(extractString(claims.get(USER_SIGNATURE_TYPE_ATTRIBUTE))
            .tenantId(userInfo.getTenantId())
            .userId(userInfo.getId())
            .userEnabled(userInfo.isEnabled())
            .build();
    }

    private SignUpRqDto makeSignUpRqDto(Map<String, Object> clames, MarketingData marketingData) {
        String[] namesArr = splitName(claims.get(NAME_ATTRIBUTE));
        SignUpRqDto signUpRqDto = SignUpRqDto.builder()
            .username(extractString(claims.get(SUB_ATTRIBUTE)))
            .applicationId(getWholesaleApplication().getId())
            .secondName(namesArr[LAST_NAME_INDEX])
            .firstName(namesArr[FIRST_NAME_INDEX])
            .middleName(extractMiddleName(MIDDLE_NAME_INDEX))
            .account(makeAccountDto(claims))
            .sbbolReqistered(true)
            .position(extractString(claims.get(POSITION_ATTRIBUTE)))
            .phone(extractPhone(claims.get(PHONE_ATTRIBUTE)))
            .individualExecutionAgency(extractBoolean(claims.get(INDIVIDUAL_EXECUTIVE_AGENCY_ATTRIBUTE)))
            .userSignatureType(extractString(claims.get(USER_SIGNATURE_TYPE_ATTRIBUTE)))
            .cid(marketingData.getCid())
            .utmCampaign(marketingData.getUtmCampaing())
            .utmTerm(marketingData.getUtmTerm())
            .utmMedium(marketingData.getUtmMedium())
            .utmSource(marketingData.getUtmSoucre())
            .referer(marketingData.getReferer())
            .build();
        
        if (!emailExist(extractString(claims.get(SUB_ATTRIBUTE)), extractString(claims.get(EMAIL_ATTRIBUTE))) {
            signUpRqDto.setEmail(extractString(claims.get(EMAIL_ATTRIBUTE)));
        }

        return signUpRqDto;
    }

    private AccountDto makeAccountDto(Map<String, Object> claims) {
        return AccountDto.build()
            .inn(extractString(claims.get(INN_ATTRIBUTE)))
            .kpp(extractString(claims.get(KPP_ATTRIBUTE)))
            .ogrn(extractString(claims.get(OGRN_ATTRIBUTE)))
            .name(extractString(claims.get(ORGANICATION_NAME_ATTRIBUTE)))
            .actualAddress(extractString(claims.get(ACTUAL_ADDRESS_ATTRIBUTE)))
            .legalAddress(extractString(claims.get(JURIDICAL_ADDRESS_ATTRIBUTE)))
            .hashSbbolId(extractString(claims.get(HASH_SBBDL_ID_ATTRIBUTE)))
            .lawFormShort(extractString(claims.get(LAW_FORM_ATTRIBUTE)))
            .sbbolRegistered(true)
            .build();
    }

    private String[] splitNames(Object nameObj) {
        if(StringUtils.isEmpty(nameObj)) {
            throw new ValidationExeption(LAST_NAME_IS_EMPTY_ERROR_MESSAGE);
        }

        ???? 
    }

    private String extractString(Object obj) {
        if(StringUtils.isEmpty(obj)){
            return null;
        }
        return (String) obj;
    }

    private String extractPhone(Object obj) {
        if(StringUtils.isEmpty(obj)){
            return null;
        }
        return ((String) obj).replaceAll(REGEX_NON_DIGITS, EMPTY_STRIGN);
    }

    private String extractBoolean(Object obj) {
        if(StringUtils.isEmpty(obj)){
            return null;
        }
        return SBBOL_TRUE_VALUE.equals(obj);
    }

    private String extractFirstName(String[] namesArr) {
        if(namesArr.length < MIDDLE_NAME_INDEX) {
            throw new ValidateionException(FIRST_NAME_IS_EMPTY_ERROR_MESSAGE);
        }

        return namesArr[FIRST_NAME_INDEX];
    }

    private String extractMiddleName(String[] namesArr) {
        return namesArr.length > MIDDLE_NAME_INDEX ? namesArr[MIDDLE_NAME_INDEX] : null;
    }
    
    private boolean emailExist(String userName, String email) {
        return userInfoRepositry.findByUsernameOrEmail(userName, email).isPresent();
    }

    /**
     * TODO
     * Метод возвращает отраслевое решение для оптовой торговли.
     * В дальнейшем будет выбираться пользователем на отдельном шаге выбора приложения.
     * Метод нужно будет удалить
     */
    private ApplicationDto getWholesaleApplication() {
        try {
            return applicationFeign.getApplicationList().stream()
                .filter(application -> WHOLESALE_APPLICATION_NAME.equals(application.getName())
                .findFirst()
                .orElseThrow(ApplicationNotFoundException::new);
        } catch (FeignException e) {
            log.error("Error when requesting the application. " + e.contentUTF8(), e);
            throw new ManagementFeignException(REQUEST_APPLICATION_ERROR_MESSAGE);
        }
    }
}
