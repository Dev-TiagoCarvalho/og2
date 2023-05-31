package dev.online.entity.ldap.account;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "locked_account")
public class AccountLockEntity implements Serializable {

    @Id @Column(name = "account_id")
    private int id;
    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    @Column(name = "lock_reason", nullable = false, columnDefinition = "text")
    private String reason;
    public String getReason() { return this.reason; }
    public void setReason(String reason) { this.reason = reason; }

    @Column(name = "locked_issued_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date issuedDate;
    public Date getIssuedDate() { return this.issuedDate; }
    public void setIssuedDate(Date issuedDate) { this.issuedDate = issuedDate; }

    @Column(name = "lock_issued_by", nullable = false)
    private int issuedBy;
    public int getIssuedBy() { return this.issuedBy; }
    public void setIssuedBy(int issuedBy) { this.issuedBy = issuedBy; }

    /*@MapsId @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private AccountEntity account;
    public AccountEntity getAccount() { return this.account; }
    public void setAccount(AccountEntity account) { this.account = account; }*/
}