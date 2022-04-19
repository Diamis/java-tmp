package ru.sbrf.sbercrm.saas.auth.service;

import java.io.IOException;
import java.util.Map;
import ru.sbrf.sbercrm.saas.auth.model.MarketingData;
import ru.sbrf.sbercrm.saas.auth.model.UserInfo;

public class SsoUserService {
    /**
     * Регистрация пользователя через SSO Провайдера
     * 
     * @param claims - пользовательский данные полученные от SSO провайдера
     * @param marketingData - информация о источнике пользователя
     * 
     * @throws IOException ошибка сети
     */
    void singUp(Map<String, Object> claims, MarketingData marketingData) throws IOException;

    /**
     * Обновление данных пользователя зарегистрированного через SSO Провайдера
     * 
     * @param claims - пользовательский данные полученные от SSO провайдера
     * @param userInfo - пользователь которого нужно обновить
     * 
     * @throws IOException ошибка сети
     */
    void singUp(Map<String, Object> claims, UserInfo userInfo) throws IOException;
}
