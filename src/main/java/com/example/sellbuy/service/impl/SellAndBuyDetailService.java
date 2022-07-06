package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.model.entity.UserRoleEntity;
import com.example.sellbuy.securityUser.SellAndBuyUserDetails;
import com.example.sellbuy.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

public class SellAndBuyDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public SellAndBuyDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return this.userRepository.
                findByEmail(username).map(this::mapUser).
                orElseThrow(()-> new UsernameNotFoundException("User with email " +
                        username + " not found!"));
    }

    private UserDetails mapUser(UserEntity userEntity){

        return new SellAndBuyUserDetails(userEntity.getId(), userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.
                        getRoles().
                        stream().
                        map(this::mapToGrantedAuthority).
                        collect(Collectors.toList())
        );
    }

    private GrantedAuthority mapToGrantedAuthority(UserRoleEntity userRole) {
        return new SimpleGrantedAuthority("ROLE_" +
                userRole.
                        getRole().name());
    }

}
