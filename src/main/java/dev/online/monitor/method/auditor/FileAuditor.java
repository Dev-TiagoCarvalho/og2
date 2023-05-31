package dev.online.monitor.method.auditor;

import dev.online.monitor.method.component.FileWriterService;
import dev.online.monitor.method.model.AuditInfo;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Map;

@Component
public class FileAuditor implements Auditor {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
    private final FileWriterService service;

    public FileAuditor(FileWriterService service) {
        this.service = service;
    }

    @Override
    public boolean audit(AuditInfo info) {
        StringBuilder builder = new StringBuilder();
        switch(info.Status()) {
            case Running -> formatStartMessage(info, builder);
            case Finished -> formatSuccessMessage(info, builder);
            case Error -> formatFailedMessage(info, builder);
        }
        service.writeLine(builder.toString());
        return true;
    }

    private static void formatStartMessage(AuditInfo info, StringBuilder builder) {
        builder.append(FORMATTER.format(info.getTimestamp())).append(" - ");
        builder.append("START   - ");
        builder.append(info.getMethod()).append('@').append(info.getId());
        if(info.getArguments().size() != 0) {
            builder.append('(');
            boolean hasPrevious = false;
            for(Map.Entry<String, Object> entry : info.getArguments().entrySet()) {
                if(!hasPrevious) { builder.append(entry.getKey()).append(": ").append(entry.getValue().toString()); hasPrevious = true; }
                else builder.append(", ").append(entry.getKey()).append(": ").append(entry.getValue().toString());
            }
            builder.append(')');
        }
        builder.append(" - ").append(info.getTraceId());
    }

    private static void formatSuccessMessage(AuditInfo info, StringBuilder builder) {
        builder.append(FORMATTER.format(info.Timestamp())).append(" - ");
        builder.append("SUCCESS - ");
        builder.append(info.getMethod()).append('@').append(info.getId()).append(':');
        builder.append(info.getResult().toString()).append(" - ");
        builder.append(info.getTraceId()).append(" - ");
        builder.append(info.getExecutionTime()).append("ms");
    }

    private static void formatFailedMessage(AuditInfo info, StringBuilder builder) {
        builder.append(FORMATTER.format(info.Timestamp())).append(" - ");
        builder.append("FAILED  - ");
        builder.append(info.getMethod()).append('@').append(info.getId()).append(" - ");
        builder.append(info.getTraceId()).append(" - ");
        builder.append(info.getExecutionTime()).append("ms");
        builder.append('\n').append(info.getException().toString());
    }
}
