package com.example.digitalmuseum.dao;

import com.example.digitalmuseum.model.User;

import java.util.UUID;

public interface UserDao {
    int insertUser(UUID id, User user);

    default int addUser(User user){
        UUID id = UUID.randomUUID();
        return insertUser(id,user);
    }
}
