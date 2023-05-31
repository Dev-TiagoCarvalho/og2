package dev.online.entity.audit.api;

import dev.online.utils.KeyValuePair;

import java.util.List;

public class RequestElasticsearchEntity {

    private long timestamp;
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    private String uri;
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }

    private String api;
    public String getApi() { return api; }
    public void setApi(String api) { this.api = api; }

    private String version;
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    private String resource;
    public String getResource() { return resource; }
    public void setResource(String resource) { this.resource = resource; }

    private String method;
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    private List<KeyValuePair<String, String>> parameters;
    public List<KeyValuePair<String, String>> getParameters() { return parameters; }
    public void setParameters(List<KeyValuePair<String, String>> parameters) { this.parameters = parameters; }

    private List<KeyValuePair<String, String>> headers;
    public List<KeyValuePair<String, String>> getHeaders() { return headers; }
    public void setHeaders(List<KeyValuePair<String, String>> headers) { this.headers = headers; }

    private Object payload;
    public Object getPayload() { return payload; }
    public void setPayload(Object payload) { this.payload = payload; }
}
