package dev.online.repository.ldap;

import dev.online.entity.ldap.account.AccountDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostgreSQLAccountDetailsRepository extends JpaRepository<AccountDetailsEntity, Integer> {

    @Query(value = "select * from ldap.public.account_management where account_id = ?1", nativeQuery = true)
    public AccountDetailsEntity findById(int id);

}
