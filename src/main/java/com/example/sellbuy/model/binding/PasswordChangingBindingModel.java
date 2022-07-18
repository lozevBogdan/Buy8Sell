package com.example.sellbuy.model.binding;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PasswordChangingBindingModel {

    @NotNull
    @Size(min = 3, max = 20)
    private String oldPassword;

    @NotNull
    @Size(min = 3, max = 20)
    private String newPassword;

    @NotNull
    @Size(min = 3, max = 20)
    private String newPasswordConfirm;

    public PasswordChangingBindingModel() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }

    public void setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
    }
}
