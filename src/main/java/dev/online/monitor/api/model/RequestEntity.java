package dev.online.monitor.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.online.utils.KeyValuePair;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static dev.online.utils.HashedIdGenerator.generateHashedId;

public class RequestEntity {

    public static final String DEFAULT_API = "[API NOT FOUND]";
    public static final String DEFAULT_VERSION = "[VERSION NOT FOUND]";
    public static final String DEFAULT_RESOURCE = "[RESOURCE NOT FOUND]";

    private static final String DEFAULT_URI_PATH = "/";

    private static final ObjectMapper jsonMapper = new ObjectMapper();

    private final String id; // value -> APIGW:a0d859b0-37c0-4bc2-8e7e-ff3d5df3e248:5de26665
    private final long timestamp; // value -> 1682596976247
    private final URI uri; // value -> http://192.168.56.109:8080/api/v1/audit/
    private final String api; // value -> audit
    private final String version; // value -> v1
    private final String resource; // value -> /
    private final String method; // value -> GET
    private final Set<KeyValuePair<String, String>> parameters = new HashSet<>(); //value -> [{key: "pretty", value: "true"}, {key: "order", value: "asc"}]
    private final Set<KeyValuePair<String, String>> headers = new HashSet<>(); //value -> [{key: "User-Agent", value: "postman"}]
    private Object payload; // values -> '{"obj":{"key":"value","int":1},"array":[{"string":"value","index":1}]}'

    public RequestEntity(String url, String method) {
        this.timestamp = System.currentTimeMillis();
        if(url == null || url.isBlank()) url = DEFAULT_URI_PATH;
        this.uri = URI.create(url);
        final String path = this.uri.getPath();
        this.id = generateHashedId(AuditEntity.REQUEST_ID_TEMPLATE, path.getBytes(StandardCharsets.UTF_8));
        this.method = method.toUpperCase();
        ParsedURI_DTO dto = uriParser(path);
        this.api = dto.api;
        this.version = dto.version;
        this.resource = dto.resource;
    }

    private static class ParsedURI_DTO {
        public String api;
        public String version;
        public String resource;
    }

    private static ParsedURI_DTO uriParser(String path) {
        ParsedURI_DTO dto = new ParsedURI_DTO();
        char[] chars = path.toCharArray();

        int indexOfVersion = 0;
        int indexOfAPI = 0;
        int indexOfResource = path.length();

        int occurrences = 0;
        parser: for(int i = 1; i < chars.length; ++i) {
            if(chars[i] == '/') {
                switch(occurrences++) {
                    case 0 -> { indexOfVersion = i; }
                    case 1 -> { indexOfAPI = i; }
                    case 2 -> { indexOfResource = i; break parser; } 
                }
            }
        }

        try {
            dto.version = path.substring(indexOfVersion + 1, indexOfAPI);
            if(dto.version.isBlank()) dto.version = DEFAULT_VERSION;
            dto.api = path.substring(indexOfAPI + 1, indexOfResource);
            if(dto.api.isBlank()) dto.api = DEFAULT_API;
            dto.resource = path.substring(indexOfResource);
            if(dto.resource.isBlank()) dto.resource = "/";
        } catch(IndexOutOfBoundsException e) {
            if(dto.version == null) dto.version = DEFAULT_VERSION;
            if(dto.api == null) dto.api = DEFAULT_API;
            if(dto.resource == null) dto.resource = DEFAULT_RESOURCE;
        }

        return dto;
    }

    public long getTimestamp() { return this.timestamp; }     
    @JsonIgnore public String getId() { return this.id; }
    public String getUri() { return this.uri.toString(); }
    public String getApi() { return this.api; }
    public String getVersion() { return this.version; }
    public String getResource() { return this.resource; }
    public String getMethod() { return this.method; }
    public Collection<KeyValuePair<String, String>> getParameters() { return this.parameters; }
    public Collection<KeyValuePair<String, String>> getHeaders() { return this.headers; }
    public Object getPayload() { return this.payload; }
    
    public void setPayload(Object obj) { this.payload = obj; }
    public void setPayloadAsString(Object obj) {
        try { this.payload = jsonMapper.writeValueAsString(obj); }
        catch(JsonProcessingException ignored) {}
    }

    public void addParameter(String name, String value) { this.parameters.add(new KeyValuePair<>(name, value)); }
    public void addHeader(String name, String value) { this.headers.add(new KeyValuePair<>(name, value)); }
}
