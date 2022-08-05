package com.example.sellbuy.model.view.userViews;

import java.util.Objects;

public class UserChatViewModel {

    private Long id;
    private String fullName;

    public UserChatViewModel() {
    }

    public Long getId() {
        return id;
    }

    public UserChatViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserChatViewModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserChatViewModel that = (UserChatViewModel) o;
        return Objects.equals(id, that.id) && Objects.equals(fullName, that.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName);
    }
}
