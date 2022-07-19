package com.example.sellbuy.model.view.userViews;

import com.example.sellbuy.model.entity.UserRoleEntity;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class UserInfoViewModel {

    private Long id;
    @NotNull
    @Size(min = 3, max = 20)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 20)
    private String lastName;

    @NotNull
    @Size(min = 3, max = 20)
    private String mobileNumber;

    @Email()
    @NotEmpty
    private String email;

    private List<UserRoleEntity> roles;

    private boolean isAdmin;

    private LocalDateTime created;

    private LocalDateTime modified;

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

    public boolean isHaveAdminRole(){
      boolean isAdmin =  this.roles.stream().anyMatch(r->r.getRole().name().equals("ADMIN"));
        return isAdmin;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public UserInfoViewModel setAdmin(boolean admin) {
        isAdmin = admin;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public UserInfoViewModel setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public UserInfoViewModel setModified(LocalDateTime modified) {
        this.modified = modified;
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
