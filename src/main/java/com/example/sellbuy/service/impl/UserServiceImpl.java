package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.model.entity.UserRoleEntity;
import com.example.sellbuy.model.entity.enums.UserRoleEnum;
import com.example.sellbuy.repository.UserRepository;
import com.example.sellbuy.repository.UserRoleRepository;
import com.example.sellbuy.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    // private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository){      //PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        //this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }


    private void initializeRoles() {
        if (userRoleRepository.count() ==0){

            UserRoleEntity adminRole = new UserRoleEntity();
            UserRoleEntity userRole = new UserRoleEntity();

            adminRole.setRole(UserRoleEnum.ADMIN);
            userRole.setRole(UserRoleEnum.USER);

            userRoleRepository.saveAll(List.of(adminRole,userRole));


        }
    }


    private void initializeUsers() {

        if(userRepository.count() == 0) {

            UserEntity user1 = new UserEntity();
            user1.
                    setFirstName("Georgi").
                    setLastName("Georgiev").
                    setEmail("gosho@abv.bg").
                    setMobileNumber("0888888888").
                    setUsername("gosho").
                    setPassword("gosho");

            UserEntity user2 = new UserEntity();
            user2.
                    setFirstName("Petyr").
                    setLastName("Petrow").
                    setEmail("petyr@abv.bg").
                    setMobileNumber("0999999999").
                    setUsername("petyr").
                    setPassword(("petyr"));

            UserEntity user3 = new UserEntity();
            user3.
                    setFirstName("Ivan").
                    setLastName("Ivanov").
                    setEmail("ivan@abv.bg").
                    setMobileNumber("08933333333").
                    setUsername("ivan").
                    setPassword(("ivan"));

            UserRoleEntity adminRole = userRoleRepository.findByRole(UserRoleEnum.ADMIN);
            UserRoleEntity userRole = userRoleRepository.findByRole(UserRoleEnum.USER);

            user1.setRoles(Set.of(adminRole,userRole));
            user2.setRoles(Set.of(userRole));
            user3.setRoles(Set.of(userRole));

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
        }


    }

    @Override
    public void initializeUsersAndRoles() {
        initializeRoles();
        initializeUsers();
    }

    @Override
    public UserEntity getByUsername(String username) {

        return userRepository.findByUsername(username).orElse(null);
    }
}
