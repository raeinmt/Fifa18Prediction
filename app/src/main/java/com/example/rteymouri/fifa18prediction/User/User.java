package com.example.rteymouri.fifa18prediction.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A dummy item representing a piece of content.
 */
public class User {
    private final String name;
    private final String email;
    private final String userId;


    public User(String name, String email, String userId) {

        this.name = name;
        this.email = email;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }
}

