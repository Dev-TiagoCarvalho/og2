package dev.online.entity.audit.api;

public class ServerElasticsearchEntity {

    private String ip;
    public String getIp() { return this.ip; }
    public void setIp(String ip) { this.ip = ip; }

    private String hostname;
    public String getHostname() { return this.hostname; }
    public void setHostname(String hostname) { this.hostname = hostname; }

    private int port;
    public int getPort() { return this.port; }
    public void setPort(int port) { this.port = port; }
}
