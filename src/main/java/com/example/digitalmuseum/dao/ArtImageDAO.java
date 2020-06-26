package com.example.digitalmuseum.dao;


import com.example.digitalmuseum.model.ArtImage;
import com.example.digitalmuseum.model.ArtItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtImageDAO extends JpaRepository<ArtImage,Integer>{
    public List<ArtImage> findByArtItemAndTypeOrderByIdDesc(ArtItem artItem, String type);

}
