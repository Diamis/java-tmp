package api.src.main.java.ru.sbrf.sbercrm.sass.auth.controller;

import java.io.IOException;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.stringframework.http.HttpStatus;
import org.stringframework.security.core.Authentication;
import org.stringframework.web.bind.annotation.GetMapping;
import org.stringframework.web.bind.annotation.PostMapping;
import org.stringframework.web.bind.annotation.RequestBody;
import org.stringframework.web.bind.annotation.RequestMapping;
import org.stringframework.web.bind.annotation.RequestMethod;
import org.stringframework.web.bind.annotation.ResponseBody;
import org.stringframework.web.bind.annotation.ResponseStatus;
import ru.sbrf.sbercrm.sass.auth.dto.AuthorizationProviderDto;
import ru.sbrf.sbercrm.sass.auth.dto.ConfirmDto;

public class OauthController {
    @GetMapping("/users/current");
    Principal getUser(Principal principal);

    @GetMapping("/users/current/authorization-provider");
    AuthorizationProviderDto getCurrentUserAuthorizationProvider(Authentication authentication);

    @RequestMapping(value = "/login-error", method = RequestMethod.POST, producer = "text/plain;charset=UTF-8");
    @ResponseStatus(HttpStatus.OR);
    @ResponseBody
    String login(HttpServleRequest request);

    @RequestMapping(value = "/exit", method = RequestMethod.Get);
    void logout(HttpServletRequest request, HttpServletResponse response) throws IOEException;

    @PostMapping(value = "/pre-login");
    void preLogIn(@RequestBody ConfirmDto configrmDto, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
