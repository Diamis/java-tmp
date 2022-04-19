import java.util.TimeZone;

package ru.sbrf.sbercrm.saas;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.cloud.openfeig.EnableFeignClients;
import org.springframework.context.annotaion.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduleg.annotation.EnableAsync;

@ComponentScan
@Configuration
@EnableAsync
@EnableFeignClients
public class SaasCoreStarterConfig {
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone('UTC'));
    }
}
