package com.example.digitalmuseum.dao;
import com.example.digitalmuseum.Util.AuthUtil;
import com.example.digitalmuseum.model.Security.AppRole;
import com.example.digitalmuseum.payload.AppUserForm;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.digitalmuseum.model.Security.User;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class UserDAO {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AppRoleDAO appRoleDAO;

    public User findAppUserByUserId(Long userId) {
        try {
            String sql = "select e from " + User.class.getName() + " e where e.userId = :userId ";
            Query query = entityManager.createQuery(sql, User.class);
            query.setParameter("userId", userId);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User findAppUserByUserName(String userName) {
        try {
            String sql = "select e from " + User.class.getName() + " e "
                    + " where e.username = :userName ";
            Query query = entityManager.createQuery(sql, User.class);
            query.setParameter("userName", userName);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User findByEmail(String email) {
        try {
            String sql = "select e from " + User.class.getName() + " e "
                    + " where e.email = :email ";
            Query query = entityManager.createQuery(sql, User.class);
            query.setParameter("email", email);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private String findAvailableUserName(String userName_prefix) {
        User account = this.findAppUserByUserName(userName_prefix);
        if (account == null) {
            return userName_prefix;
        }
        int i = 0;
        while (true) {
            String userName = userName_prefix + "_" + i++;
            account = this.findAppUserByUserName(userName);
            if (account == null) {
                return userName;
            }
        }
    }

    // Auto create App User Account.
    public User createAppUser(Connection<?> connection) {
        ConnectionKey key = connection.getKey();
        System.out.println("key= (" + key.getProviderId() + "," + key.getProviderUserId() + ")");
        UserProfile userProfile = connection.fetchUserProfile();
        String email = userProfile.getEmail();
        User appUser = this.findByEmail(email);
        if (appUser != null) {
            return appUser;
        }
        String userName_prefix = userProfile.getFirstName().trim().toLowerCase()
                + "_" + userProfile.getLastName().trim().toLowerCase();

        String userName = this.findAvailableUserName(userName_prefix);
        String randomPassword = UUID.randomUUID().toString().substring(0, 5);
        String encrytedPassword = AuthUtil.encrytePassword(randomPassword);
        appUser = new User();
        appUser.setEnabled(true);
        appUser.setEncrytedPassword("{bcrypt}"+encrytedPassword);
        appUser.setUsername(userName);
        appUser.setEmail(email);
        this.entityManager.persist(appUser);
        // Create default Role
        List<String> roleNames = new ArrayList<String>();
        roleNames.add(AppRole.ROLE_USER);
        this.appRoleDAO.createRoleFor(appUser, roleNames);

        return appUser;
    }

    public User registerNewUserAccount(AppUserForm appUserForm, List<String> roleNames) {
        User appUser = new User();
        appUser.setUsername(appUserForm.getUserName());
        appUser.setEmail(appUserForm.getEmail());
        appUser.setEnabled(true);
        String encrytedPassword = AuthUtil.encrytePassword(appUserForm.getPassword());
        appUser.setEncrytedPassword("{bcrypt}"+encrytedPassword);
        this.entityManager.persist(appUser);
        this.entityManager.flush();

        this.appRoleDAO.createRoleFor(appUser, roleNames);

        return appUser;
    }
}