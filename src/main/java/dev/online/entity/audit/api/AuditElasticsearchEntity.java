package dev.online.entity.audit.api;

public class AuditElasticsearchEntity {

    private String id;
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    private long executionTime;
    public long getExecutionTime() { return this.executionTime; }
    public void setExecutionTime(long executionTime) { this.executionTime = executionTime; }

    private RequestElasticsearchEntity request;
    public RequestElasticsearchEntity getRequest() { return this.request; }
    public void setRequest(RequestElasticsearchEntity request) { this.request = request; }

    private ResponseElasticsearchEntity response;
    public ResponseElasticsearchEntity getResponse() { return this.response; }
    public void setResponse(ResponseElasticsearchEntity response) { this.response = response; }

    private ServerElasticsearchEntity server;
    public ServerElasticsearchEntity getServer() { return this.server; }
    public void setServer(ServerElasticsearchEntity server) { this.server = server; }

    private UserElasticsearchEntity user;
    public UserElasticsearchEntity getUser() { return this.user; }
    public void setUser(UserElasticsearchEntity user) { this.user = user; }
}
