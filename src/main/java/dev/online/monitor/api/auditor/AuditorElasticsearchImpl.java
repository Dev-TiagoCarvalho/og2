package dev.online.monitor.api.auditor;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.CreateRequest;
import dev.online.entity.audit.api.AuditElasticsearchEntity;
import dev.online.mapper.audit.api.ElasticsearchAuditEntityMapper;
import dev.online.monitor.api.component.AuditIndexNameProvider;
import dev.online.monitor.api.model.AuditEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuditorElasticsearchImpl implements Auditor {

    private final ElasticsearchClient client;
    private final ElasticsearchAuditEntityMapper mapper;

    public AuditorElasticsearchImpl(ElasticsearchClient client, ElasticsearchAuditEntityMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    @Override
    public boolean audit(AuditEntity info) {
        String index = AuditIndexNameProvider.elasticsearchIndex(info.request.getApi());
        CreateRequest.Builder<AuditElasticsearchEntity> document = new CreateRequest.Builder<>();
        document.document(mapper.map(info));
        document.id(info.getId());
        document.index(index);
        try {
            client.create(document.build());
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
