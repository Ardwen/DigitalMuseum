package com.example.digitalmuseum.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.digitalmuseum.model.User;
public interface UserDAO extends JpaRepository<User,Integer>{

}
