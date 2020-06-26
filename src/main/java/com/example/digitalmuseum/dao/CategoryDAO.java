package com.example.digitalmuseum.dao;

import com.example.digitalmuseum.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository<Category,Integer> {
}
