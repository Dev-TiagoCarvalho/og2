package dev.online.monitor.api.component;

import dev.online.monitor.api.model.AuditEntity;
import dev.online.monitor.api.model.RequestEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.concurrent.ConcurrentHashMap;

public class AuditIndexNameProvider {

    public static final String DEFAULT_API_IDENTIFIER = "default";

    private static final ConcurrentHashMap<String, String> CACHE = new ConcurrentHashMap<>();
    private static final String INDEX_NAME_TEMPLATE = "audit-api-";

    public static String elasticsearchIndex(HttpServletRequest request) {
        AuditEntity entity = (AuditEntity) request.getAttribute("dev.online.audit.model.AuditEntity");
        String api = entity.request.getApi();
        if(api.equals(RequestEntity.DEFAULT_API)) api = DEFAULT_API_IDENTIFIER;
        if(CACHE.containsKey(api)) return CACHE.get(api);
        return CACHE.put(api, INDEX_NAME_TEMPLATE + api);
    }

    public static String elasticsearchIndex(String api) {
        if(api.equals(RequestEntity.DEFAULT_API)) api = DEFAULT_API_IDENTIFIER;
        if(CACHE.containsKey(api)) return CACHE.get(api);
        CACHE.put(api, INDEX_NAME_TEMPLATE + api);
        return CACHE.get(api);
    }
}
