package com.example.digitalmuseum.model.Security;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Userconnection")
public class UserConnection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "User_id", length = 50, nullable = false)
    private String userId;


    @Column(name = "Providerid", nullable = false)
    private String providerId;


    @Column(name = "Provideruserid", nullable = false)
    private String providerUserId;

    @Column(name = "Rrank", nullable = false)
    int rrank;

    @Column(name = "Displayname", length = 255, nullable = true)
    private String displayName;

    @Column(name = "Profileurl", length = 512, nullable = true)
    private String profileUrl;

    @Column(name = "Imageurl", length = 512, nullable = true)
    private String imageUrl;

    @Column(name = "Accesstoken", length = 512, nullable = true)
    private String accessToken;

    @Column(name = "Secret", length = 512, nullable = true)
    private String secret;

    @Column(name = "Refreshtoken", length = 512, nullable = true)
    private String refreshToken;

    @Column(name = "Expiretime", nullable = true)
    private Long expireTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public int getRank() {
        return rrank;
    }

    public void setRank(int rank) {
        this.rrank = rank;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

}