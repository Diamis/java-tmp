package ru.sbrf.sbercrm.saas.service;

import java.util.List;
import java.util.UUID;
import ru.sbrf.sbercrm.saas.model.DvmCondition;

public interface BusinessObjectDvmCheckService {
    void checkObjectByDvmConditions(UUID entityId, UUID objectId, List<DvmCodition> conditions);

    void checkObjectByStateDvmCoditions(UUID entityId, UUID objectId, UUID stateId);
}
