package ru.sbrf.sbercrm.saas.auth.service;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class CacheService {
    Map<String, HttpServletRequest> getCache();
}
