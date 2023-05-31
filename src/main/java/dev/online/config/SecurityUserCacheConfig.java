package dev.online.config;

import dev.online.repository.ldap.PostgreSQLAccountDetailsRepository;
import dev.online.security.component.AccountDetailsManagementService;
import dev.online.security.component.UserCacheExpirationService;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;

import java.util.Timer;

@EnableCaching
@Configuration
public class SecurityUserCacheConfig {

    public final static String USER_CACHE_NAME = "USER_CACHE";
    public final static Timer DEAMON_TIMER = new Timer(true);

    @Bean
    public ConcurrentMapCacheManager concurrentMapCacheManager() {
        return new ConcurrentMapCacheManager(USER_CACHE_NAME);
    }

    @Bean
    public UserCache userCache() {
        ConcurrentMapCacheManager concurrentMapCacheManager = concurrentMapCacheManager();
        return new SpringCacheBasedUserCache(concurrentMapCacheManager.getCache(USER_CACHE_NAME));
    }

    @Bean
    public UserCacheExpirationService userCacheExpirationService() {
        return new UserCacheExpirationService(DEAMON_TIMER, userCache());
    }
}
