package dev.online.monitor.method.model;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.UUID;

public class AuditInfo {

    private final String traceId;

    private final String auditId = UUID.randomUUID().toString();;
    private long startTime;
    private final String method;
    private final Map<String, Object> arguments;

    private long endTime;
    private AuditStatus status;
    private Object result;
    private WritableException exception;

    public AuditInfo(String name, Map<String, Object> args) {
        this.traceId = null;
        this.method = name;
        this.arguments = args;
        this.status = AuditStatus.Running;
    }

    public AuditInfo(@Nonnull String traceId, String name, Map<String, Object> args) {
        this.traceId = traceId;
        this.method = name;
        this.arguments = args;
        this.status = AuditStatus.Running;
    }

    public String getTraceId() { return this.traceId; }
    public String getId() { return this.auditId; }
    public String getMethod() { return this.method; }
    public Map<String, Object> getArguments() { return this.arguments; }

    public long getTimestamp() { return this.startTime; }
    public void Init(long timestamp) { this.startTime = timestamp; }

    public long Timestamp() { return this.endTime; }
    public long getExecutionTime() { return this.endTime - this.startTime; }
    public void Timestamp(long timestamp) { this.endTime = timestamp; }

    public AuditStatus Status() { return this.status; }
    public String getStatus() { return this.status.name(); }
    public void Status(AuditStatus status) { this.status = status; }

    public Object getResult() { return this.result; }
    public void Result(Object result) { this.result = result; }

    public WritableException getException() { return this.exception; }
    public void Exception(Throwable ex) { this.exception = new WritableException(ex); }
}
