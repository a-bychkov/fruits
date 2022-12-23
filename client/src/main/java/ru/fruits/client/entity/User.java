package ru.fruits.client.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.fruits.client.dto.Role;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @ElementCollection(targetClass = Role.class, fetch = EAGER)
    @Enumerated(EnumType.STRING)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role", nullable = false)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = this.roles.stream()
                .map(e -> new SimpleGrantedAuthority(e.name()))
                .toList();
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;//todo: process this
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;//todo: process this
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;//todo: process this
    }

    @Override
    public boolean isEnabled() {
        return true;//todo: process this
    }
}
