package com.example.feleves_projekt.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class LoginResponse {
    @SerializedName("userid")
    @Expose
    private Long userId;
    @SerializedName("displayname")
    @Expose
    private String displayName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public LoginResponse(Long pUserId, String pDisplayName) {
        this.userId = pUserId;
        this.displayName = pDisplayName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginResponse loginResponse = (LoginResponse) o;
        return displayName.equals(loginResponse.displayName) && userId.equals(loginResponse.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, displayName);
    }
}
