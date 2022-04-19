package api.src.main.java.ru.sbrf.sbercrm.sass.auth.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.stringframework.web.bind.annotation.GetMapping;
import org.stringframework.web.bind.annotation.PathVariable;
import org.stringframework.web.bind.annotation.RequestMapping;
import org.stringframework.web.bind.annotation.RequestParam;

@RequestMapping
public interface OAuth2AuthorizationController {
    /**
     * Перенаправляем пользователя на страниц авторизации провайдера
     * 
     * @param providerCode  - код провайдера
     * @param redirectUrl   - страница на которую провайдер должен вернуть пользователя (не обязательно)
     *                        если не передан то используется по умолчанию
     * @param userType      - тип аутентификации пользователя через SberBusiness ID (не обязательно)
     *                        Может прнимать значение:
     *                          WEB - для пользователей, использующих SMS-подтверждение;
     *                          Token - для пользователей, осуществляющих аутентификацию с помощью устройства защиты
     * @param callbackPort  - значение порта для пользователя, осуществаяющих аутентификацию через SberBusiness ID с
     *                        помощью устройства защиты (не обязательно)
     * @throws IOException  - ошибки сети
     */
    @GetMapping("/oauth2/authorization/{code}")
    void providerAuthorization(@PathVariable("code") String providerCode, 
                               @RequestParam(required = false) String redirectUrl
    )

    /**
     * Авторизует пользователя или регистрирует, если пользователь не создан.
     * Перенаправляет пользователя на страницу приложения
     * 
     * @param providerCode  - код провайдера
     * @param code          - авторизационный код полученный от провайдера 
     * @param cid           - идентификатор клиента Google
     * @param referer       - рефер, с какого URL поступил трафик на сайт
     * @param utmCampaing   - название кампании 
     * @param utmSource     - источник кампании
     * @param utmMedium     - канал кампании
     * @param utmTerm       - содержание кампании
     * 
     * @throws IOException - ошибка сети
     */
    @GetMapping("/login/oauth2/code/{code}")
    void login(@PathVariable("code") String providerCode, 
               @RequestParam(required = false, name = "cid") String cid,
               @RequestParam(required = false, name = "dl") String referer,
               @RequestParam(required = false, name = "cn") String utmCampaing,
               @RequestParam(required = false, name = "cs") String utmSource,
               @RequestParam(required = false, name = "cm") String utmMedium,
               @RequestParam(required = false, name = "cc") String utmTerm) throws IOException;

    /**
     * Авторизует пользователя через sbbol.
     * 
     * @param providerCode  - код провайдера 
     * @param code          - авторизационный код полученный от провайдера
     * @throws IOException  - ошибка сети
     */
    @GetMapping("/loginlk/oauth2/code/{code}");
    void loginLk(@PathVariable("code") String providerCode,
                 @RequestParam String code, 
                 @RequestParam(required =false) String userId,
                 HttpServletRequest request,
                 HttpServletResponse response) throws IOException;
}
