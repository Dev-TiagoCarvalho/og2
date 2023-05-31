package dev.online.monitor.api.component;

import dev.online.monitor.api.auditor.Auditor;
import dev.online.monitor.api.model.AuditEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncAuditService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncAuditService.class);
    private final Auditor auditor;

    public AsyncAuditService(Auditor auditor) {
        this.auditor = auditor;
    }

    @Async
    public void audit(AuditEntity entity) {
        LOGGER.debug("START API AUDIT - {}", entity.getId());
        boolean result = auditor.audit(entity);
        if(result) LOGGER.debug("SUCCESS API AUDIT - {}", entity.getId());
        else LOGGER.warn("FAILED API AUDIT - {} with {}", entity.getId(), entity);
    }
}
