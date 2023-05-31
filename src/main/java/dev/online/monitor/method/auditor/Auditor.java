package dev.online.monitor.method.auditor;

import dev.online.monitor.method.model.AuditInfo;

public interface Auditor {

    public boolean audit(AuditInfo info);
}
