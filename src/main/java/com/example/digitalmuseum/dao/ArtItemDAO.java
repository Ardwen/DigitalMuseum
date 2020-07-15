package com.example.digitalmuseum.dao;

import com.example.digitalmuseum.model.ArtItem;
import com.example.digitalmuseum.model.Museume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtItemDAO extends JpaRepository<ArtItem,Integer> {
    List<ArtItem> findByMuseume(Museume museume);
    Page<ArtItem> findByMuseume(Museume museume, Pageable pageable);
}
