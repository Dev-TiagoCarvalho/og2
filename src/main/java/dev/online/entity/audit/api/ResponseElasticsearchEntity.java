package dev.online.entity.audit.api;

import dev.online.utils.KeyValuePair;

import java.util.List;

public class ResponseElasticsearchEntity {

    private long timestamp;
    public long getTimestamp() { return this.timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    private int statusCode;
    public int getStatusCode() { return this.statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    private String statusMessage;
    public String getStatusMessage() { return this.statusMessage; }
    public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }

    private String status;
    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }

    private List<KeyValuePair<String, String>> headers;
    public List<KeyValuePair<String, String>> getHeaders() { return this.headers; }
    public void setHeaders(List<KeyValuePair<String, String>> headers) { this.headers = headers; }

    private Object body;
    public Object getBody() { return this.body; }
    public void setBody(Object body) { this.body = body; }
}
