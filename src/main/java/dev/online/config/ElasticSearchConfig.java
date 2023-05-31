package dev.online.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;

@Configuration
@EnableElasticsearchRepositories
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.rest.uris}")
    private String connection;

    @Value("${spring.elasticsearch.rest.username}")
    private String username;

    @Value("${spring.elasticsearch.rest.password}")
    private String password;

    @Override
    public ClientConfiguration clientConfiguration() {
        try {
            SSLContext tls = sslContext(certificate, certPassword);
            return ClientConfiguration.builder().connectedTo(connection).usingSsl(tls).withBasicAuth(username, password).build();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Value("${http.client.tls.truststore.location}")
    private String certificate;

    @Value("${http.client.tls.truststore.password}")
    private String certPassword;

    private static SSLContext sslContext(String keystoreFile, String password) throws GeneralSecurityException, IOException {
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        try (InputStream in = new FileInputStream(keystoreFile)) { keystore.load(in, password.toCharArray()); }
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keystore, password.toCharArray());

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keystore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());

        return sslContext;
    }
}
