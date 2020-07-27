package com.example.digitalmuseum.Security.Jwt;

import com.example.digitalmuseum.dao.AppRoleDAO;
import com.example.digitalmuseum.dao.UserDAO;
import com.example.digitalmuseum.model.Security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDAO appUserDAO;

    @Autowired
    private AppRoleDAO appRoleDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User appUser = this.appUserDAO.findAppUserByUserName(username);

        if (appUser == null) {
            System.out.println("User not found! " + username);
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        System.out.println("Found User: " + appUser);

        List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }
        return new org.springframework.security.core.userdetails.User(appUser.getUsername(),appUser.getEncrytedPassword(),grantList);

    }
}
