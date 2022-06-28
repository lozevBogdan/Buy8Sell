package com.example.sellbuy.service;

import com.example.sellbuy.model.entity.UserRoleEntity;
import com.example.sellbuy.model.entity.enums.UserRoleEnum;

public interface UserRoleService {

     void initializeRoles();

     UserRoleEntity findByRole(UserRoleEnum userRoleEnum);
}
