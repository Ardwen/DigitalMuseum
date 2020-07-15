package com.example.digitalmuseum.dao;

import com.example.digitalmuseum.model.Museume;
import com.example.digitalmuseum.model.MuseumeImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MuseumeImageDAO extends JpaRepository<MuseumeImage,Integer> {
    public List<MuseumeImage> findByMuseumeAndTypeOrderByIdDesc(Museume museume, String type);
}
