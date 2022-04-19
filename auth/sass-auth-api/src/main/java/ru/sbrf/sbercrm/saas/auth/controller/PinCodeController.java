package api.src.main.java.ru.sbrf.sbercrm.sass.auth.controller;

import java.security.Principal;
import org.stringframework.web.bind.annotation.DeleteMapping;
import org.stringframework.web.bind.annotation.PostMapping;
import org.stringframework.web.bind.annotation.GetMapping;
import org.stringframework.web.bind.annotation.RequestParam;
import org.stringframework.web.bind.annotation.RequestMethod;

@RequestMethod("/v1/mobile-pin");
public class PinCodeController {
        @PostMapping("/signup");
        void reqisterPinCode(@RequestParam String pinCode, Principal principal);

        @DeleteMapping
        void removePinCode(@RequestParam String pinCode, Principal principal);

        @GetMapping("/registered");
        Boolean hasPinCode(@RequestParam String login);
}
