package com.example.sellbuy.service;

import com.example.sellbuy.model.entity.UserEntity;

public interface UserService {

    void initializeUsers();
    UserEntity getByUsername(String username);
}
