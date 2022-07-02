package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.entity.UserRoleEntity;
import com.example.sellbuy.model.entity.enums.UserRoleEnum;
import com.example.sellbuy.repository.UserRoleRepository;
import com.example.sellbuy.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void initializeRoles() {

        if (this.userRoleRepository.count() == 0) {
            UserRoleEntity adminRole = new UserRoleEntity();
            UserRoleEntity userRole = new UserRoleEntity();
            adminRole.setRole(UserRoleEnum.ADMIN);
            userRole.setRole(UserRoleEnum.USER);
            userRoleRepository.saveAll(List.of(adminRole, userRole));
        }

    }

    @Override
    public UserRoleEntity findByRole(UserRoleEnum userRoleEnum) {
        return this.userRoleRepository.findByRole(userRoleEnum);
    }


}