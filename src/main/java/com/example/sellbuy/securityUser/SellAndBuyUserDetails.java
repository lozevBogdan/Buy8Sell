package com.example.sellbuy.securityUser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
// this is representation of Springs UserDetails,
// because we add more field like a : firstname an lastName
//here we defined which field will expose
public class SellAndBuyUserDetails implements UserDetails {

    private final Long id;
    private final String password;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final Collection<GrantedAuthority> authorities;

    public SellAndBuyUserDetails(Long id, String password, String username,
                                 String firstName, String lastName,
                                 Collection<GrantedAuthority> authorities) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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

    public Long getId() {
        return id;
    }
}
