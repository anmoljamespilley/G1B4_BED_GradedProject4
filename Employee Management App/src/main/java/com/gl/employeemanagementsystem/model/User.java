package com.gl.employeemanagementsystem.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    // Default constructor
    public User() {
    }

    // Constructor with username, password, and roles arguments
    public User(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    // UserDetails methods

    // Retrieves the authorities granted to the user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    // Indicates whether the user's account has expired
    @Override
    public boolean isAccountNonExpired() {
        // Account expiration logic can be implemented here if needed
        return true;
    }

    // Indicates whether the user is locked or unlocked
    @Override
    public boolean isAccountNonLocked() {
        // Account locking logic can be implemented here if needed
        return true;
    }

    // Indicates whether the user's credentials (password) has expired
    @Override
    public boolean isCredentialsNonExpired() {
        // Credential expiration logic can be implemented here if needed
        return true;
    }

    // Indicates whether the user is enabled or disabled
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
