package com.example.sellbuy.securityUser;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

// After implement Spring security, this class is NOT used !
@Component
@SessionScope
public class CurrentUser {

    private Long id;

    private String email;

    public CurrentUser() {
    }

    public Long getId() {
        return id;
    }

    public CurrentUser setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CurrentUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public void logInCurrUser(Long id,String email){
        this.id = id;
        this.email = email;
    }

    public boolean isLogged(){
        return (this.id != null && this.email != null);
    }
}
