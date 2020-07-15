package com.example.digitalmuseum.Security;

import com.example.digitalmuseum.dao.UserDAO;
import com.example.digitalmuseum.model.Security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserDAO userDAO;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //UserDetails principal = (UserDetails) authentication.getPrincipal();
        //String username = principal.getUsername();
        //User user = userDAO.findAppUserByUserName(username);
        httpServletResponse.setStatus(HttpStatus.OK.value());
    }
}

