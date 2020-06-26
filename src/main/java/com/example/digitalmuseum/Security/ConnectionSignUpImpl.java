package com.example.digitalmuseum.Security;

import com.example.digitalmuseum.dao.UserDAO;
import com.example.digitalmuseum.model.Security.User;
import com.example.digitalmuseum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

public class ConnectionSignUpImpl implements ConnectionSignUp {

    private UserDAO appUserDAO;

    public ConnectionSignUpImpl(UserDAO appUserDAO) {
        this.appUserDAO = appUserDAO;
    }


    // After logging in social networking.
    // This method will be called to create a corresponding App_User record
    // if it does not already exist.
    @Override
    public String execute(Connection<?> connection) {

        User account = appUserDAO.createAppUser(connection);
        return account.getUsername();
    }

}
