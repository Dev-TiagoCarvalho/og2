package dev.online.monitor.api.model;

public class UserEntity {

    private int id;
    private String username;
    private String email;
    private boolean enabled;
    private String role;

    public UserEntity() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean getEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}
