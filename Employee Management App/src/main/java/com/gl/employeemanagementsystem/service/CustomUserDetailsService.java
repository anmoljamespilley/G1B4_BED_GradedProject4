package com.gl.employeemanagementsystem.service;

import com.gl.employeemanagementsystem.model.Role;
import com.gl.employeemanagementsystem.model.User;
import com.gl.employeemanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by username and constructs a UserDetails object.
     *
     * @param username the username of the user to load
     * @return a UserDetails object representing the loaded user
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve user details from the database based on the username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Retrieve authorities (roles) for the user
        Collection<GrantedAuthority> authorities = getAuthorities(user.getRoles());

        // Construct a UserDetails object representing the user
        return buildUserDetails(user, authorities);
    }

    /**
     * Retrieves authorities (roles) for the user.
     *
     * @param roles the roles associated with the user
     * @return a collection of GrantedAuthority objects representing the user's roles
     */
    private Collection<GrantedAuthority> getAuthorities(Set<Role> roles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    /**
     * Constructs a UserDetails object based on the user and authorities.
     *
     * @param user        the user whose details are being loaded
     * @param authorities the authorities (roles) granted to the user
     * @return a UserDetails object representing the user
     */
    private UserDetails buildUserDetails(User user, Collection<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(),
                authorities
        );
    }
}
