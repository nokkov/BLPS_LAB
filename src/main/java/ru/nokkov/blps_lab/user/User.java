package ru.nokkov.blps_lab.user;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Table(name = "article_user")
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isModerator;

    @Column(nullable = false)
    private boolean isManager;

    @Column(nullable = false)
    private boolean isAdmin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        if (isAdmin) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        if (isManager) {
            authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        }
        if (isModerator) {
            authorities.add(new SimpleGrantedAuthority("ROLE_MODERATOR"));
        }

        return authorities;
    }

    // jaas (ﾉಥ益ಥ)ﾉ
    public Set<String> getRoles() {
        Set<String> roles = new HashSet<>();

        if (isAdmin) {
            roles.add("ROLE_ADMIN");
        }
        if (isManager) {
            roles.add("ROLE_MANAGER");
        }
        if (isModerator) {
            roles.add("ROLE_MODERATOR");
        }

        return roles;
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
