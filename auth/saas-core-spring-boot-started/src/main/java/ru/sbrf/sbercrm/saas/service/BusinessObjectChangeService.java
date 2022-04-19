package ru.sber.sbercrm.saas.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import ru.sbrf.sbercrm.saas.dto.filterDto;
import ru.sbrf.sbercrm.saas.dto.BusinessObjectChangeRqDto;

public interface BusinessObjectChangeService {
    Map<Stirng, Object> create(UUID entityId, Map<String, Object> request);

    Map<String, Object> create(UUID entityId, Map<String, Object> request, UUID objectId);

    List<Map<String, Object>> createObjects(UUID entityId, List<Map<String, Object>> request);

    Map<Stirng, Object> create(String entity, Map<String, Object> request);

    Map<Stirng, Object> create(String entity, Map<String, Object> request, UUID objectId);

    Map<Stirng, Object> create(String entity, Map<String, Object> request, UUID objectId, boolean updateReadOnly);

    // Создание системного объекта
    Map<Stirng, Object> createSystemObject(String entity, Map<String, Object> request);
    
    Map<Stirng, Object> update(String entity, Map<String, Object> request, UUID objectId);
    
    Map<Stirng, Object> update(UUID entityId, Map<String, Object> request, UUID objectId);
    
    Map<Stirng, Object> update(String entity, Map<String, Object> request, UUID objectId, boolean updateReadOnly);
    
    Map<Stirng, Object> update(UUID entityId, Map<String, Object> request, UUID objectId, boolean updateReadOnly);

    List<Map<String, Object>> updateObjects(UUID entityId, List<Map<String, Object>> requestList);
    
    void updateAll(UUID entityId, Set<UUID> objectIds, Map<String, Object> request);

    void delete(UUID entityId, UUID objectId);

    void delete(String entity, UUID objectId);

    void deleteAll(UUID entityId, Set<UUID> objectIds);
    
    void forEachByFilters(UUID entityId, Set<FilterDto> filters, Consumer<Map<String, Object>> consumer);
}
