package dev.online.mapper.audit.api;

import co.elastic.clients.util.Pair;
import dev.online.entity.audit.api.*;
import dev.online.mapper.Mapper;
import dev.online.monitor.api.model.*;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchAuditEntityMapper implements Mapper<AuditEntity, AuditElasticsearchEntity> {

    @Override
    public AuditElasticsearchEntity map(AuditEntity obj) {
        AuditElasticsearchEntity entity = new AuditElasticsearchEntity();
        entity.setId(obj.getId());
        entity.setExecutionTime(obj.getExecutionTime());
        entity.setRequest(mapRequestElasticsearchEntity(obj.request));
        entity.setResponse(mapResponseElasticsearchEntity(obj.response));
        entity.setServer(mapServerElasticsearchEntity(obj.server));
        entity.setUser(mapUserElasticsearchEntity(obj.user));
        return entity;
    }

    private UserElasticsearchEntity mapUserElasticsearchEntity(UserEntity user) {
        UserElasticsearchEntity entity = new UserElasticsearchEntity();
        entity.setId(user.getId());
        entity.setEmail(user.getEmail());
        entity.setUsername(user.getUsername());
        entity.setRole(user.getRole());
        entity.setEnabled(user.getEnabled());

        return entity;
    }

    private ServerElasticsearchEntity mapServerElasticsearchEntity(ServerEntity server) {
        ServerElasticsearchEntity entity = new ServerElasticsearchEntity();
        entity.setIp(server.getIp());
        entity.setHostname(server.getHostname());
        entity.setPort(server.getPort());

        return entity;
    }

    private ResponseElasticsearchEntity mapResponseElasticsearchEntity(ResponseEntity response) {
        ResponseElasticsearchEntity entity = new ResponseElasticsearchEntity();
        entity.setStatusCode(response.getStatusCode());
        entity.setStatusMessage(response.getStatusMessage());
        entity.setStatus(response.getStatus().name());
        entity.setTimestamp(response.getTimestamp());
        entity.setHeaders(response.getHeaders().stream().toList());
        entity.setBody(response.getPayload());

        return entity;
    }

    private RequestElasticsearchEntity mapRequestElasticsearchEntity(RequestEntity request) {
        RequestElasticsearchEntity entity = new RequestElasticsearchEntity();
        entity.setUri(request.getUri());
        entity.setApi(request.getApi());
        entity.setVersion(request.getVersion());
        entity.setResource(request.getResource());
        entity.setMethod(request.getMethod());
        entity.setTimestamp(request.getTimestamp());
        entity.setParameters(request.getParameters().stream().toList());
        entity.setHeaders(request.getHeaders().stream().toList());
        entity.setPayload(request.getPayload());

        return entity;
    }
}
