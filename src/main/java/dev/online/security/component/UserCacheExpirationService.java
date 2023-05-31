package dev.online.security.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserCache;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class UserCacheExpirationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCacheExpirationService.class);
    private final UserCache cache;
    private final Timer timer;

    public UserCacheExpirationService(Timer timer, UserCache cache) {
        this.cache = cache;
        this.timer = timer;
    }

    public void evictUserAt(String username, Date timestamp) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                LOGGER.info("Evicting user {} from UserCache", username);
                try {
                    cache.removeUserFromCache(username);
                } catch(Exception e) {
                    LOGGER.error("Exception when trying to evict user from UserCache", e);
                }
            }
        }, timestamp);
    }
}
