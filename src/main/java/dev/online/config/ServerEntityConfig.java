package dev.online.config;

import dev.online.monitor.api.model.ServerEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class ServerEntityConfig {

    private final ServerPortService service;

    public ServerEntityConfig(ServerPortService service) {
        this.service = service;
    }

    @Bean("defaultServerEntity")
    public ServerEntity defaultServerEntity() {
        return new ServerEntity(service);
    }
    
}
