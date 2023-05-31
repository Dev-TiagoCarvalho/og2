package dev.online.entity.ldap.account;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "account_info")
public class AccountEntity implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int id;
    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    @Column(name = "account_email", nullable = false, length = 50)
    private String email;
    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    @Column(name = "account_username", nullable = false, length = 20)
    private String username;
    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }

    @Column(name = "account_mobile_number", length = 16)
    private String mobileNumber;
    public String getMobileNumber() { return this.mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    @Column(name = "account_hashed_pass", nullable = false, length = 60)
    private String hashedPassword;
    public String getHashedPassword() { return this.hashedPassword; }
    public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }

    /*@OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @PrimaryKeyJoinColumn
    private AccountDetailsEntity accountDetails;
    public AccountDetailsEntity getAccountDetails() { return this.accountDetails; }
    public void setAccountDetails(AccountDetailsEntity accountDetails) { this.accountDetails = accountDetails; }*/

    /*@OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    @PrimaryKeyJoinColumn
    private AccountLockEntity accountLock;
    public AccountLockEntity getAccountLock() { return this.accountLock; }
    public void setAccountLock(AccountLockEntity accountLock) { this.accountLock = accountLock; }

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<AccountRequestEntity> requests;
    public List<AccountRequestEntity> getRequests() { return this.requests; }
    public void setRequests(List<AccountRequestEntity> requests) { this.requests = requests; }*/
}
