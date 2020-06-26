package com.example.digitalmuseum.model;


import javax.persistence.*;

public class ArtItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @ManyToOne
    @JoinColumn(name="mid")
    private Museume museume;

    private String name;
    private String intro;
    @Transient
    private ArtImage firstArtImage;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Museume getMuseume() {
        return museume;
    }

    public void setMuseume(Museume museume) {
        this.museume = museume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public ArtImage getFirstArtImage() {
        return firstArtImage;
    }

    public void setFirstArtImage(ArtImage firstArtImage) {
        this.firstArtImage = firstArtImage;
    }
}
