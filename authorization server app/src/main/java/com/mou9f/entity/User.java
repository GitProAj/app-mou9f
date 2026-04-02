package com.mou9f.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "uesr-role-name",
            joinColumns = @JoinColumn(name="user-column"),
            inverseJoinColumns = @JoinColumn(name="role-column")
        )
    private Set<Role> roles;

    private boolean expired=false;
    private boolean locked=false;
    private boolean credentialExpired=false;
    private boolean disabled=false;

//    public String getPassword() {
//        return password;
//    }
//
//    public String getUsername() {
//        return username;
//    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

//    @Override
//    public String getPassword() {
//        return "";
//    }
//
//    @Override
//    public String getUsername() {
//        return "";
//    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialExpired;
    }

    @Override
    public boolean isEnabled() {
        return !disabled;
    }
}
