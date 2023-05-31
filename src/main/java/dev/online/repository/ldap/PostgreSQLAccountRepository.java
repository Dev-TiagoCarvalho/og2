package dev.online.repository.ldap;

import dev.online.entity.ldap.account.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostgreSQLAccountRepository extends JpaRepository<AccountEntity, Integer> {

    @Query(value = "select * from ldap.public.account_info where account_email = lower(?1) or account_username = lower(?1)", nativeQuery = true)
    public Optional<AccountEntity> findAccountEntityByUsernameOrEmail(String value);

}
