package com.example.digitalmuseum.model.Security;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users",uniqueConstraints = {@UniqueConstraint(columnNames = {"UserId","Email"})})
public class User {

    @Id
    @GeneratedValue
    @Column(name = "UserId", nullable = false)
    private Long userId;

    @Column(name = "UserName", nullable = false)
    private String username;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Encryted_Password",  nullable = true)
    private String encrytedPassword;

    @Column(name = "Enabled", nullable = false)
    private boolean enabled;


    @Column(name = "Role")
    private String role;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncrytedPassword() {
        return encrytedPassword;
    }

    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
