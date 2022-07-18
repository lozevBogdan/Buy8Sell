package com.example.sellbuy.model.view.userViews;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserEditViewModel {

    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 20)
    private String lastName;

    @Email
    @NotEmpty
    private String email;

    @NotNull
    @Size(min = 10, max = 10)
    private String mobileNumber;

    public UserEditViewModel() {
    }

    public Long getId() {
        return id;
    }

    public UserEditViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEditViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEditViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEditViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public UserEditViewModel setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }
}
