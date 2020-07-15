package com.example.digitalmuseum.model;


import com.example.digitalmuseum.model.Security.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "Museume")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
public class Museume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @ManyToOne
    @JoinColumn(name="cid")
    private Category category;

    @ManyToOne
    @JoinColumn(name="uid")
    private User user;

    private String name;
    private String introduction;
    private String country;
    private String city;
    private String link;

    @Transient
    private MuseumeImage firstmuImage;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public MuseumeImage getFirstmuImage() {
        return firstmuImage;
    }

    public void setFirstmuImage(MuseumeImage firstmuImage) {
        this.firstmuImage = firstmuImage;
    }
}
