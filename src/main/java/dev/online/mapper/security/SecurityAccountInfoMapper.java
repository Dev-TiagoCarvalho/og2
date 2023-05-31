package dev.online.mapper.security;

import dev.online.entity.ldap.account.AccountEntity;
import dev.online.mapper.Mapper;
import dev.online.security.model.SecurityAccountInfoDTO;
import org.springframework.stereotype.Component;

@Component
public class SecurityAccountInfoMapper implements Mapper<AccountEntity, SecurityAccountInfoDTO> {
    @Override
    public SecurityAccountInfoDTO map(AccountEntity obj) {
        return new SecurityAccountInfoDTO(obj.getId(), obj.getEmail(), obj.getUsername(), obj.getHashedPassword());
    }
}
