package com.example.digitalmuseum.dao;

import com.example.digitalmuseum.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class fakeuserdataAccess implements UserDao {
    private static List<User> DB = new ArrayList<>();

    @Override
    public int insertUser(UUID id, User user) {
        DB.add(new User(id,user.getName(),user.getEmail()));
        return 1;
    }
}
