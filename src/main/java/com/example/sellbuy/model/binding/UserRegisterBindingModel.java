package com.example.sellbuy.model.binding;

import com.example.sellbuy.model.entity.MessageEntity;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.entity.UserRoleEntity;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class UserRegisterBindingModel {

    @Email(message = "The email, should be valid!")
    @NotEmpty(message = "The email can not be null!")
    private String email;

    @NotNull(message = "The password can not be null!")
    @Size(min = 3, max = 20,message = "The password should be between 3 and 20 symbols!")
    private String password;

    @NotNull(message = "The confirm password can not be null!")
    @Size(min = 3, max = 20,message = "The password should be between 3 and 20 symbols!")
    private String confirmPassword;

    @NotNull(message = "The first name can not be null!")
    @Size(min = 3, max = 20, message = "The first name should be between 3 and 20 symbols!")
    private String firstName;

    @NotNull(message = "The last name can not be null!")
    @Size(min = 3, max = 20, message = "The last name should be between 3 and 20 symbols!")
    private String lastName;

    @NotNull(message = "The mobile number can not be null!")
    @Size(min = 10, max = 10, message = "The mobile number should be ten numbers!")
    private String mobileNumber;

    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegisterBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegisterBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public UserRegisterBindingModel setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    @Override
    public String toString() {
        return "UserRegisterBindingModel{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                '}';
    }
}
