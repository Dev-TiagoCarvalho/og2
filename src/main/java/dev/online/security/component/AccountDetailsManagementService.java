package dev.online.security.component;

import dev.online.entity.ldap.account.AccountDetailsEntity;
import dev.online.repository.ldap.PostgreSQLAccountDetailsRepository;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class AccountDetailsManagementService {

    private final PostgreSQLAccountDetailsRepository repository;
    private final Timer timer;

    public AccountDetailsManagementService(Timer timer, PostgreSQLAccountDetailsRepository repository) {
        this.timer = timer;
        this.repository = repository;
    }

    public void invalidateCredentialsAt(AccountDetailsEntity entity, Date timestamp) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                entity.setCredentialsExpired(false);
                repository.save(entity);
            }
        }, timestamp);
    }
}
