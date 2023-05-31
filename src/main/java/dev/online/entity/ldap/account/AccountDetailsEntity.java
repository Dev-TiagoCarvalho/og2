package dev.online.entity.ldap.account;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "account_management")
public class AccountDetailsEntity implements Serializable {
    
    @Id @Column(name = "account_id")
    private int id;
    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    @Column(name = "account_role", nullable = false, length = 10)
    private String role;
    public String getRole() { return this.role; }
    public void setRole(String role) { this.role = role; }

    @Column(name = "account_non_expired", nullable = false)
    private boolean expired;
    public boolean getExpired() { return this.expired; }
    public void setExpired(boolean expired) { this.expired = expired; }

    @Column(name = "account_non_locked", nullable = false)
    private boolean locked;
    public boolean getLocked() { return this.locked; }
    public void setLocked(boolean locked) { this.locked = locked; }

    @Column(name = "account_credentials_non_expired", nullable = false)
    private boolean credentialsExpired;
    public boolean getCredentialsExpired() { return this.credentialsExpired; }
    public void setCredentialsExpired(boolean credentialsExpired) { this.credentialsExpired = credentialsExpired; }

    @Column(name = "account_enabled", nullable = false)
    private boolean enabled;
    public boolean getEnabled() { return this.enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    @Column(name = "account_activate_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activationDate;
    public Date getActivationDate() { return this.activationDate; }
    public void setActivationDate(Date activationDate) { this.activationDate = activationDate; }

    @Column(name = "account_credentials_expiration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date credentialsExpirationDate;
    public Date getCredentialsExpirationDate() { return this.credentialsExpirationDate; }
    public void setCredentialsExpirationDate(Date credentialsExpirationDate) { this.credentialsExpirationDate = credentialsExpirationDate; }

    /*@MapsId @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private AccountEntity account;
    public AccountEntity getAccount() { return this.account; }
    public void setAccount(AccountEntity account) { this.account = account; }*/
}
