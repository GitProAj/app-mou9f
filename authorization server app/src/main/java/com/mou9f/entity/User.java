package com.mou9f.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User implements UserDetails {

//    @Override
//    public boolean isEnabled() {
//        return enabled && isSubscriptionValid();
//    }
//
//    public boolean isSubscriptionValid() {
//        if (!subscriptionActive) return false;
//
//        if (trialPeriod && trialEndDate != null) {
//            return LocalDateTime.now().isBefore(trialEndDate);
//        }
//
//        if (subscriptionEndDate != null) {
//            return LocalDateTime.now().isBefore(subscriptionEndDate);
//        }
//
//        return false;
//    }

//    public boolean hasAccessToFeature(String feature) {
//        if (!isSubscriptionValid()) return false;
//
//        return switch (subscriptionPlan) {
//            case "BASIC" -> Set.of("read", "basic").contains(feature);
//            case "PREMIUM" -> Set.of("read", "write", "premium").contains(feature);
//            case "ENTERPRISE" -> true;
//            default -> false;
//        };
//    }

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
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

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
        return enabled;
    }
    @Column
    private LocalDateTime subscriptionStartDate;
    @Column
    private LocalDateTime subscriptionEndDate;
    @Column
    private boolean trialPeriod = true;
    @Column
    private LocalDateTime trialEndDate;

}



