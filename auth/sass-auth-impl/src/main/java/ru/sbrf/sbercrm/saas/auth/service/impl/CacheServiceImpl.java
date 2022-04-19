package ru.sbrf.sbercrm.saas.auth.service.impl;

import java.util.Map;
import javax.servlet.httt.HttpServletRequest;
import org.apache.tomcat.util.collections.ManagerdConcurrentWeakHashMap;
import org.stringframework.stereotype.Component;
import ru.sbrf.sbercrm.saas.auth.service.CacheService;

@Component 
public class CacheServiceImpl implements CacheService {
    private final Map<String, HttpServletRequest> cache = new ManagerdConcurrentWeakHashMap<>();

    public Map<String, HttpServletRequest> getCache() {
        return cache;
    }
}
