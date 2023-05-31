package dev.online.security.component;

import dev.online.security.model.SecurityAccountDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public record UserDetailsPostgreSQLImpl(SecurityAccountDTO account) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(() -> account.details().role());
    }

    @Override
    public String getPassword() {
        return account.info().hashedPassword();
    }

    @Override
    public String getUsername() {
        return account.info().username();
    }

    @Override
    public boolean isAccountNonExpired() {
        return account.details().expired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return account.details().locked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return account.details().credentialsExpired();
    }

    @Override
    public boolean isEnabled() {
        return account.details().enabled();
    }
}
