package com.example.digitalmuseum.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "musumeImage")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
public class MuseumeImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name="mid")
    @JsonBackReference
    private Museume museume;

    private String type;

    public String getType() {
        return type;
    }

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

    public void setType(String type) {
        this.type = type;
    }
}
