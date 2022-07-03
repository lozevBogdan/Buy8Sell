package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.model.entity.UserRoleEntity;
import com.example.sellbuy.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository.
                findByEmail(username).
                map(this::map).
                orElseThrow(() ->
                        new UsernameNotFoundException("User with email " + username + " not found!"));
    }

    // here we map Userentity to UserDetail which Spring Security understand and used
    private UserDetails map(UserEntity userEntity) {
        return
                // User is default representation of UserDetails in Spring Security
                User.builder().
                        username(userEntity.getEmail()).
                        password(userEntity.getPassword()).
                        authorities(userEntity.
                                getRoles().
                                stream().
                                map(this::map).
                                collect(Collectors.toList())).
                        build();
    }

    // every role which we return, must be with prefix "ROLE_"
    private GrantedAuthority map(UserRoleEntity userRole) {
        return new SimpleGrantedAuthority("ROLE_" +
                userRole.
                        getRole().name());
    }

}
