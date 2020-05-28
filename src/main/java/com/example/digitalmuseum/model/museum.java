package com.example.digitalmuseum.model;

import java.util.List;
import java.util.UUID;

public class Museum {
    private final UUID id;
    private final String museumName;
    private final List<String> tags;

    public Museum(UUID id, String museum_name, List<String> tags) {
        this.id = id;
        this.museumName = museum_name;
        this.tags = tags;
    }

    public String getMuseumName() {
        return museumName;
    }

    public List<String> getTags() {
        return tags;
    }
}
