package com.example.digitalmuseum.payload;

import java.util.List;

public class ArtPost {
        String name;
        String description;
        int museumeId;
        List<Integer> images;

    public int getMuseumeId() {
        return museumeId;
    }

    public void setMuseumeId(int museumeId) {
        this.museumeId = museumeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getImages() {
        return images;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }
}
