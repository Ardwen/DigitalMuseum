package com.example.digitalmuseum.dao;

import com.example.digitalmuseum.model.Category;
import com.example.digitalmuseum.model.Museume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MuseumeDAO extends JpaRepository<Museume,Integer> {
    public Museume findByName(String name);
    public Page<Museume> findByCategory(Category category, Pageable pageable);
    public Page<Museume> findByCountry(String country, Pageable pageable);
    public Page<Museume> findByCategoryAndCountry(Category category,String country,Pageable pageable);
    public Page<Museume> findAll(Pageable pageable);
}
