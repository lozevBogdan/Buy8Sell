package com.example.sellbuy.model.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLoginBindingModel {

    @Email(message = "The email, should be valid!")
    @NotEmpty(message = "The email can not be null!")
    private String email;

    @NotNull(message = "The password can not be null!")
    @Size(min = 3, max = 20,message = "The password should be between 3 and 20 symbols!")
    private String password;

    public String getEmail() {
        return email;
    }

    public UserLoginBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
