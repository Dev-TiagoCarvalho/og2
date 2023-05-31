package dev.online.monitor.method.auditor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.online.monitor.method.model.AuditInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleAuditor implements Auditor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleAuditor.class);
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Override
    public boolean audit(AuditInfo info) {
        try { LOGGER.info(JSON_MAPPER.writeValueAsString(info)); }
        catch (JsonProcessingException e) { return false; }
        return true;
    }
}
