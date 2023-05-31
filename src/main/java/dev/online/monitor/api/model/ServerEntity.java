package dev.online.monitor.api.model;

import dev.online.config.ServerPortService;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerEntity {

    private final String ip; // value -> 192.168.56.109
    private final String hostname; // value -> localhost

    private final ServerPortService service;

    public ServerEntity(ServerPortService service) {
        this.ip = lookupHostAddress();
        this.hostname = lookupHostName();
        this.service = service;
    }

    private static String lookupHostAddress() {
        try { return InetAddress.getLocalHost().getHostAddress(); }
        catch(UnknownHostException _ignored) { return "127.0.0.1"; }
    }

    private static String lookupHostName() {
        try { return InetAddress.getLocalHost().getHostName(); }
        catch(UnknownHostException _ignored) { return "localhost"; }
    }
    
    public String getIp() { return this.ip; }
    public String getHostname() { return this.hostname; }
    public int getPort() { return this.service.getPort(); }
}
