package dev.online.entity.ldap.user;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_settings")
@SecondaryTable(name = "user_permissions")
public class UserEntity implements Serializable {

    @Id @Column(name = "account_id")
    private int id;
    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    //TODO: Create Enum that will handle this values;
    @Column(name = "display_theme")
    private String displayTheme;
    public String getDisplayTheme() { return this.displayTheme; }
    public void setDisplayTheme(String displayTheme) { this.displayTheme = displayTheme; }

    @Column(name = "display_name")
    private String displayName;
    public String getDisplayName() { return this.displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
}
