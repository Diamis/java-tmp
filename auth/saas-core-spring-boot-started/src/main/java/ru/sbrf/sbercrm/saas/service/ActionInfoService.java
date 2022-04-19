public class ActionInfoService {
    
}
package ru.sbrf.sbercrm.saas.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import ru.sbrf.sbercrm.saas.model.ActionInfo;

/**
 * Сервис для получения информации
 * по доступным действия в процессах
 */
public interface ActionInfoService {
    List<ActionInfo> getAllActionInfo();

    ActionInfo getActionInfo();

    Optional<ActionInfo> getActionInfoByName(String name);

    ActionInfo add(ActionInfo actionInfo);
}