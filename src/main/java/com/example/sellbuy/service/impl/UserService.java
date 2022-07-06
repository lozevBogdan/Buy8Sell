package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final UserMapper userMapper;
//    private UserDetailsService userDetailsService;
//
//    public UserService(UserRepository userRepository,
//                       PasswordEncoder passwordEncoder,
//                       UserMapper userMapper,
//                       UserDetailsService userDetailsService) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.userMapper = userMapper;
//        this.userDetailsService = userDetailsService;
//    }
//
//    public void registerAndLogin(UserRegisterDTO userRegisterDTO) {
//
//        UserEntity newUser = userMapper.userDtoToUserEntity(userRegisterDTO);
//        newUser.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
//
//        this.userRepository.save(newUser);
//        login(newUser);
//    }
//
//
//    private void login(UserEntity userEntity) {
//        UserDetails userDetails =
//                userDetailsService.loadUserByUsername(userEntity.getEmail());
//
//        Authentication auth =
//                new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        userDetails.getPassword(),
//                        userDetails.getAuthorities()
//                );
//
//        SecurityContextHolder.
//                getContext().
//                setAuthentication(auth);
//    }

}
