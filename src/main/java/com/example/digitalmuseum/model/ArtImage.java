package com.example.digitalmuseum.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "artImage")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
public class ArtImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name="aid")
    @JsonBackReference
    private ArtItem artItem;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArtItem getArtItem() {
        return artItem;
    }

    public void setArtItem(ArtItem artItem) {
        this.artItem = artItem;
    }
}
