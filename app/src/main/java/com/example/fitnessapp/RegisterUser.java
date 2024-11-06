package com.example.fitnessapp;

import com.example.fitnessapp.models.User;

public class RegisterUser {

    private static RegisterUser instance;

    private User user;

    public static RegisterUser getInstance(){
        if(instance == null){
            instance = new RegisterUser();
        }

        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
