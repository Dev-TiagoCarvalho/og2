package dev.online.mapper.security;

import dev.online.entity.ldap.account.AccountDetailsEntity;
import dev.online.mapper.Mapper;
import dev.online.security.model.SecurityAccountDetailsDTO;
import org.springframework.stereotype.Component;

@Component
public class SecurityAccountDetailsMapper implements Mapper<AccountDetailsEntity, SecurityAccountDetailsDTO> {
    @Override
    public SecurityAccountDetailsDTO map(AccountDetailsEntity obj) {
        return new SecurityAccountDetailsDTO(obj.getRole(), obj.getExpired(), obj.getLocked(), obj.getCredentialsExpired(), obj.getEnabled(), obj.getCredentialsExpirationDate());
    }
}
