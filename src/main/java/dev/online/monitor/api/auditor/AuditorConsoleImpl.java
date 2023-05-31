package dev.online.monitor.api.auditor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.online.monitor.api.model.AuditEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditorConsoleImpl implements Auditor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditorConsoleImpl.class);
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Override
    public boolean audit(AuditEntity info) {
        try {
            LOGGER.info(JSON_MAPPER.writeValueAsString(info));
            return true;
        }
        catch(JsonProcessingException e) { return false; }
    }
}
