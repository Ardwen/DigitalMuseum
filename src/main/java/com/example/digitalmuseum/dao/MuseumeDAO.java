package com.example.digitalmuseum.dao;

import com.example.digitalmuseum.model.Category;
import com.example.digitalmuseum.model.Museume;
import com.example.digitalmuseum.model.Security.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MuseumeDAO extends JpaRepository<Museume,Integer> {
    public Museume findByName(String name);
    public List<Museume> findByCategory(Category category);
    public List<Museume> findByCountry(String country);
    public List<Museume> findByCategoryAndCountry(Category category,String country);
    public List<Museume> findAll();
    public List<Museume> findByUser(User user);
}
