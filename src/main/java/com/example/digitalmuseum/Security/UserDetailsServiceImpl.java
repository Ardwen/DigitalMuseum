package com.example.digitalmuseum.Security;

import com.example.digitalmuseum.dao.AppRoleDAO;
import com.example.digitalmuseum.dao.UserDAO;
import com.example.digitalmuseum.model.Security.AppRole;
import com.example.digitalmuseum.model.Security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDAO appUserDAO;

    @Autowired
    private AppRoleDAO appRoleDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("UserDetailsServiceImpl.loadUserByUsername=" + username);
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

        SocialUserDetailsImpl userDetails = new SocialUserDetailsImpl(appUser, roleNames);

        return userDetails;
    }

}
