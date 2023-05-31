package dev.online.config;

import dev.online.repository.ldap.PostgreSQLAccountDetailsRepository;
import dev.online.security.component.AccountDetailsManagementService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;

@Configuration
public class SpringSecurityConfig {

    private final PostgreSQLAccountDetailsRepository repository;

    public SpringSecurityConfig(PostgreSQLAccountDetailsRepository repository) {
        this.repository = repository;
    }

    @Bean
    public AccountDetailsManagementService accountDetailsManagementService() {
        return new AccountDetailsManagementService(SecurityUserCacheConfig.DEAMON_TIMER, repository);
    }

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }
}
