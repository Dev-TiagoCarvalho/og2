package dev.online.security.model;

import java.util.Date;

public record SecurityAccountDetailsDTO(
        String role,
        boolean expired,
        boolean locked,
        boolean credentialsExpired,
        boolean enabled,
        Date credentialsExpirationDate) {
}
