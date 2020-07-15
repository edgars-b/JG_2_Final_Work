package com.javaguru.onlineshop.user.login;

import com.javaguru.onlineshop.user.UserRoles;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

public class UserLoginDTO {

    private Long id;
    @NotEmpty(message = "Login must not be empty.")
    @Size(min = 3, max = 50, message = "Login must be between 3 and 50 characters long.")
    private String username;
    @NotEmpty(message = "Password must not be empty.")
    private String password;
    private boolean isActive;
    private Set<UserRoles> roles;

    public UserLoginDTO() {
    }

    public UserLoginDTO(Long id, String username, String password, boolean isActive, Set<UserRoles> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Set<UserRoles> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRoles> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserLoginDTO{" +
                "id=" + id +
                ", login='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", roles=" + roles +
                '}';
    }
}
