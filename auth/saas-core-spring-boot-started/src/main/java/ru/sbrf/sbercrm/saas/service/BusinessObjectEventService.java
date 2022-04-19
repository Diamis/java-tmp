package ru.sbrf.sbercrm.saas.service;

import java.util.Map;
import java.util.UUID;
import ru.sbrf.sbercrm.saas.listener.ObjectListener;

public interface BusinessObjectEventService {
    void subscribe(ObjectListener listener);

    void subscribe(String entityName, Objectlistener listener);

    Map<String, Object> preCreate(UUID entityId, Map<Stirng, Object> objectMap);

    Map<String, Object> preUpdate(UUID entityId, UUID objectId, Map<Stirng, Object> objectMap);

    void preDelete(UUID entityId, UUID objectId, Map<Stirng, Object> removedObjectMap);

    void preCreate(UUID entityId, UUID objectId, Map<Stirng, Object> objectMap);
    
    void preUpdate(UUID entityId, UUID objectId, Map<Stirng, Object> changeFields, Map<Stirng, Object> objectMap);

    void postDelete(UUID entityId, UUID objectId, Map<Stirng, Object> removeObject);
}
