package dev.online.monitor.api.model;

import dev.online.utils.KeyValuePair;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ResponseEntity {

    private long timestamp; // value -> 1682596976247
    private int statusCode; // value -> 201
    private String statusMessage; // -> Created
    private ResponseStatus status; // -> Success
    private final Set<KeyValuePair<String, String>> headers = new HashSet<>(); //value -> [{key: "User-Agent", value: "postman"}]
    private Object payload; // values -> '{"obj":{"key":"value","int":1},"array":[{"string":"value","index":1}]}'

    public long getTimestamp() { return this.timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public int getStatusCode() { return this.statusCode; }
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        this.status = (statusCode >= 200 && statusCode <= 299) ? ResponseStatus.Success : ResponseStatus.Failure;
    }

    public String getStatusMessage() { return this.statusMessage; }
    public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }

    public ResponseStatus getStatus() { return this.status; }

    public Collection<KeyValuePair<String, String>> getHeaders() { return this.headers; }
    public void addHeader(String name, String value) { this.headers.add(new KeyValuePair<>(name, value)); }

    public Object getPayload() { return this.payload; }
    public void setPayload(Object payload) { this.payload = payload; }
}
