package dev.online.security.component;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEvents {

    private final UserCache cache;

    public AuthenticationEvents(UserCache cache) {
        this.cache = cache;
    }

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        cache.putUserInCache((UserDetails) success.getAuthentication().getPrincipal());
    }

}
