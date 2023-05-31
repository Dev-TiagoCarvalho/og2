package dev.online.security.component;

import dev.online.entity.ldap.account.AccountDetailsEntity;
import dev.online.entity.ldap.account.AccountEntity;
import dev.online.mapper.security.SecurityAccountDetailsMapper;
import dev.online.mapper.security.SecurityAccountInfoMapper;
import dev.online.monitor.api.model.AuditEntity;
import dev.online.monitor.method.annot.Audit;
import dev.online.repository.ldap.PostgreSQLAccountDetailsRepository;
import dev.online.repository.ldap.PostgreSQLAccountRepository;
import dev.online.security.model.SecurityAccountDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServicePostgreSQLImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServicePostgreSQLImpl.class);

    private final PostgreSQLAccountRepository accountRepository;
    private final PostgreSQLAccountDetailsRepository detailsRepository;
    private final SecurityAccountInfoMapper accountMapper;
    private final SecurityAccountDetailsMapper detailsMapper;
    private final UserCacheExpirationService expirationService;
    private final AccountDetailsManagementService detailsManagementService;

    private final HttpServletRequest request;
    private final UserCache cache;

    public UserDetailsServicePostgreSQLImpl(UserCache cache, PostgreSQLAccountRepository accountRepository, PostgreSQLAccountDetailsRepository detailsRepository, SecurityAccountInfoMapper accountMapper, SecurityAccountDetailsMapper detailsMapper, UserCacheExpirationService expirationService, AccountDetailsManagementService detailsManagementService, HttpServletRequest request) {
        this.cache = cache;
        this.accountRepository = accountRepository;
        this.detailsRepository = detailsRepository;
        this.accountMapper = accountMapper;
        this.detailsMapper = detailsMapper;
        this.expirationService = expirationService;
        this.detailsManagementService = detailsManagementService;
        this.request = request;
    }

    @Audit
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Try to get user details from cache");
        String searchableUsername = username.toLowerCase();
        UserDetails userDetails = cache.getUserFromCache(searchableUsername);
        if(userDetails == null) return loadUserByUsernamePostgreSQL(searchableUsername);
        return userDetails;
    }

    private UserDetails loadUserByUsernamePostgreSQL(String username) {
        LOGGER.debug("Loading user by username or email in PostgreSQL: " + username);
        Optional<AccountEntity> optionalInfo = accountRepository.findAccountEntityByUsernameOrEmail(username);
        if(optionalInfo.isEmpty()) {
            LOGGER.info("Loading user by username not found: " + username);
            throw new UsernameNotFoundException("Invalid credentials for user: " + username);
        }
        AccountEntity accountEntity = optionalInfo.get();
        AccountDetailsEntity detailsEntity = detailsRepository.findById(accountEntity.getId());
        SecurityAccountDTO account = new SecurityAccountDTO(accountMapper.map(accountEntity), detailsMapper.map(detailsEntity));

        if(account.details().credentialsExpirationDate() != null) {
            expirationService.evictUserAt(username, account.details().credentialsExpirationDate());
            detailsManagementService.invalidateCredentialsAt(detailsEntity, account.details().credentialsExpirationDate());
        }

        UserDetailsPostgreSQLImpl details = new UserDetailsPostgreSQLImpl(account);
        logLoadUserByUsername(username, account);
        setAuditUser(account);
        return details;
    }

    private void setAuditUser(SecurityAccountDTO dto) {
        AuditEntity entity = (AuditEntity) request.getAttribute("dev.online.monitor.api.model.AuditEntity");
        if(entity == null) return;
        entity.user.setId(dto.info().id());
        entity.user.setEmail(dto.info().email());
        entity.user.setUsername(dto.info().username());
        entity.user.setRole(dto.details().role());
        entity.user.setEnabled(dto.details().enabled());
    }

    private void logLoadUserByUsername(String value, SecurityAccountDTO account) {
        LOGGER.info("User loaded by value: " + value + " where username = " + account.info().username() + " and email = " + account.info().email() + ". ");
        if(account.details() != null) LOGGER.info("The details were also loaded: account " + (account.details().enabled() ? "enabled" : "disabled") + " with role = " + account.details().role());
        else LOGGER.info("Could not load the details for ID: " + account.info().id());
    }
}
