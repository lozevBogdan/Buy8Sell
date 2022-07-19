package com.example.sellbuy.model.view.userViews;

import com.example.sellbuy.model.entity.UserRoleEntity;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class UserInfoViewModel {

    private Long id;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private List<UserRoleEntity> roles;

    public UserInfoViewModel() {
    }

    public Long getId() {
        return id;
    }

    public UserInfoViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserInfoViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserInfoViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public UserInfoViewModel setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserInfoViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public UserInfoViewModel setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    public String rolesToString(){
        String roles = "";

        for (int i = 0; i < this.roles.size(); i++) {

            if(i==1){
                roles += "/";
            }
            roles +=this.roles.get(i).getRole().name();
        }
        return roles;
    }
}
