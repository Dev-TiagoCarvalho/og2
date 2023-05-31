package dev.online.monitor.api.model;

import dev.online.config.ApplicationContextProvider;

public class AuditEntity {

    public static final String REQUEST_ID_TEMPLATE = "APIGW:";

    public final ServerEntity server;
    public final UserEntity user;
    public final RequestEntity request;
    public final ResponseEntity response;

    public AuditEntity(RequestEntity request) {
        this.request = request;
        this.server = (ServerEntity) ApplicationContextProvider.getApplicationContext().getBean("defaultServerEntity");
        this.response = new ResponseEntity();
        this.user = new UserEntity();
    }

    public String getId() { return request.getId(); }
    public long getExecutionTime() { return response.getTimestamp() - request.getTimestamp(); }
    public void setExecutionTime(long millis) { this.response.setTimestamp(millis); }
}

