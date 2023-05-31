package dev.online.entity.audit.api;

public class UserElasticsearchEntity {

    private int id;
    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    private String username;
    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }

    private String email;
    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    private String role;
    public String getRole() { return this.role; }
    public void setRole(String role) { this.role = role; }

    private boolean enabled;
    public boolean getEnabled() { return this.enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}
