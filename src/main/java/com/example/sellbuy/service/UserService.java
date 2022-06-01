package com.example.sellbuy.service;

import com.example.sellbuy.model.entity.UserEntity;

public interface UserService {


    void initializeUsersAndRoles();
    UserEntity getByUsername(String username);
}
