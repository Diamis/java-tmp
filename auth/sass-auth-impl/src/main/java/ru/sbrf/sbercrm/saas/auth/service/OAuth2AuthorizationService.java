package ru.sbrf.sbercrm.saas.auth.service;

import java.io.IOException;
import javax.servlet.http.HttpServetRequest;
import javax.servlet.http.HttpServetResponse;
import ru.sbrf.sbercrm.saas.auth.model.MarketingData;

public class OAuth2AuthorizationService {
    /**
     * Перенапраление пользователя на страницу авторизации провайдера
     * 
     * @param providerCode - код провайдера
     * @param redirectUri  - страница на которую должен перенаправить пользователь провайдера (не обязательно)
     *                       Может принимать значение:
     *                          WEB - для пользователей, использующих SMS-продтверждение;
     *                          Token - для пользователей, осуществляющих аутентификацию с помощью устройства защиты;
     * @param callbackPort - значение порта для пользователей, осуществляющих аутентификация через SberBusiness Id с
     *                       помощью устройства защиты (не обязательно)
     * @param IOException  - ошибка сети
     */
    void authorization(String providerCode, String redirectUri, String userType, String callbackPort, HttpServletRequest request, HttpServetResponse response) throws IOException;

    /**
     * Авторизация/Регистрация пользователя вошедшего через SSO провайдера
     * 
     * @param providerCode  - код провайдера
     * @param code          - авторизационный код
     * @param marketingData - информация о источнике пользователя
     * 
     * @throws IOException ошибка сети
     */
    void login(String providerCode, String code, MarketingData marketingData, HttpServletRequest request, HttpServetResponse response) throws IOException;
    
    /**
     * Регистрация пользователя через SSO провайдера в личном кабинете
     * 
     * @param providerCode  - код провайдера
     * @param code          - авторизационный код 
     * 
     * @throws IOException ошибка сети
     */
    void loginLk(String providerCode, String code, String userId, HttpServletRequest request, HttpServetResponse response) throws IOException;
}
