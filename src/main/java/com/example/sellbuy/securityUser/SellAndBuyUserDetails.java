package com.example.sellbuy.securityUser;

import com.example.sellbuy.model.entity.ProductEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

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
    private final Set<ProductEntity> favoriteProducts;

    public SellAndBuyUserDetails(Long id, String password, String username,
                                 String firstName, String lastName,
                                 Collection<GrantedAuthority> authorities, Set<ProductEntity> favoriteProducts) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorities = authorities;
        this.favoriteProducts = favoriteProducts;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Set<ProductEntity> getFavoriteProducts() {
        return favoriteProducts;
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
