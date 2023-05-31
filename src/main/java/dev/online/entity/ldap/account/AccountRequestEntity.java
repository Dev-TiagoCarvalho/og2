package dev.online.entity.ldap.account;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "account_sensitive_request")
public class AccountRequestEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private long id;
    public long getId() { return this.id; }

    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "request_token")
    private UUID token;
    public UUID getToken() { return this.token; }
    public void setToken(UUID token) { this.token = token; }

    @Column(name = "request_type")
    private String type;
    public String getType() { return this.type; }
    public void setType(String type) { this.type = type; }

    @Column(name = "request_user_confirmation")
    private boolean userConfirmation;
    public boolean getUserConfirmation() { return this.userConfirmation; }
    public void setUserConfirmation(boolean userConfirmation) { this.userConfirmation = userConfirmation; }

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;
    public AccountEntity getAccount() { return this.account; }
    public void setId(AccountEntity account) { this.account = account; }*/
}