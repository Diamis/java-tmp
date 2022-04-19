package api.src.main.java.ru.sbrf.sbercrm.sass.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.sbrf.sbercrm.sass.auth.controller.OauthController;

@FeignClient(name = "auth-users", url = "${AUTH_URL:http//localhost:9191/auth}")
public class OauthControllerFeign extends OauthController {
    
}
