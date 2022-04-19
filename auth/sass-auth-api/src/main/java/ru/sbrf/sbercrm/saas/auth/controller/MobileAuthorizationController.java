package api.src.main.java.ru.sbrf.sbercrm.sass.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sbrf.sbercrm.sass.auth.dto.AuthorizationDto;
import ru.sbrf.sbercrm.sass.auth.dto.MobileTokenDto;

@RequestMapping("/mobile")
public interface MobileAuthorizationController {
    @PostMapping("/login")
    MobileTokenDto login(@RequestBody AuthorizationDto authorizationDto)
}
