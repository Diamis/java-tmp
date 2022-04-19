package ru.sbrf.sbercrm.saas.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import ru.sbrf.sbercrm.saas.model.BusinessObjectFieldAuditValue;
import ru.sbrf.sbercrm.saas.model.BusinessObjectOperationAudit;

public interface BusinessObjectAuditService {
    void addOperationAndFieldsAudit(BusinessObjectOperationAudit operationAudit);

    List<BusinessObjectFieldAuditValue> getHistoryChangeByObjectIdAndFieldId(
        UUID objectId,
        UUID fieldId,
        Long limit
    );

    List<BusinessObjectOperationAudit> getOperationsByEntityId(
        UUID entityId,
        List<UUID> fieldIds,
        OffsetDateTime startDate,
        OffsetDateTime endDate
    );    
}
