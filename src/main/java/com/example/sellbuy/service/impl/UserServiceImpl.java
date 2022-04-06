package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.repository.UserRepository;
import com.example.sellbuy.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void initializeUsers() {

        UserEntity user1 = new UserEntity();
        user1.
                setFirstName("Georgi").
                setLastName("Georgiev").
                setEmail("gosho@abv.bg").
                setMobileNumber("0888888888").
                setUsername("gosho").
                setPassword(passwordEncoder.encode("gosho"));

        UserEntity user2 = new UserEntity();
        user2.
                setFirstName("Petyr").
                setLastName("Petrow").
                setEmail("petyr@abv.bg").
                setMobileNumber("0999999999").
                setUsername("petyr").
                setPassword(passwordEncoder.encode("petyr"));

        UserEntity user3 = new UserEntity();
        user3.
                setFirstName("Ivan").
                setLastName("Ivanov").
                setEmail("ivan@abv.bg").
                setMobileNumber("08933333333").
                setUsername("ivan").
                setPassword(passwordEncoder.encode("ivan"));

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);


    }

    @Override
    public UserEntity getByUsername(String username) {

        return userRepository.findByUsername(username).orElse(null);
    }
}
