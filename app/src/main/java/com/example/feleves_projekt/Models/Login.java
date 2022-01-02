package com.example.feleves_projekt.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Login {
    @SerializedName("loginname")
    @Expose
    private String loginname;
    @SerializedName("password")
    @Expose
    private String password;


    public Login(String loginname, String password) {
        this.loginname = loginname;
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login = (Login) o;
        return loginname.equals(login.loginname) && password.equals(login.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loginname, password);
    }

}
