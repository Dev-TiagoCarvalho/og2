package dev.online.monitor.api.auditor;

import dev.online.monitor.api.model.AuditEntity;

public interface Auditor {

    public boolean audit(AuditEntity info);

}
