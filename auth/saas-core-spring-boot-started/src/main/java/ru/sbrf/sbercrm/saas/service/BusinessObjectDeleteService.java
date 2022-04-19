package ru.sbrf.sbercrm.saas.service;

import java.util.Set;
import java.util.UUID;

public interface BusinessObjectDeleteService {
    void delete(UUID entityId, UUID objectId);

    void deleteAll(UUID entityId, Set<UUID> objectId);
}
