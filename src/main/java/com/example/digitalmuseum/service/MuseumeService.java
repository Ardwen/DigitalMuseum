package com.example.digitalmuseum.service;

import com.example.digitalmuseum.dao.MuseumeDAO;
import com.example.digitalmuseum.dao.MuseumeImageDAO;
import com.example.digitalmuseum.dao.UserDAO;
import com.example.digitalmuseum.model.ArtItem;
import com.example.digitalmuseum.model.Category;
import com.example.digitalmuseum.model.Museume;
import com.example.digitalmuseum.model.MuseumeImage;
import com.example.digitalmuseum.model.Security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.example.digitalmuseum.payload.MuPost;

@Service
public class MuseumeService {

    @Autowired
    MuseumeDAO museumeDAO;

    @Autowired
    MuseumeImageDAO museumeImageDAO;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserDAO userDAO;



    public Museume get(int id){ return museumeDAO.getOne(id);}


    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public Museume add(MuPost bean, String username){
        Museume newmu= new Museume();
        newmu.setName(bean.getTitle());
        newmu.setCountry(bean.getCountry());
        newmu.setCity(bean.getCity());
        newmu.setIntroduction(bean.getDescription());
        newmu.setCategory(categoryService.get(bean.getCategory()));
        newmu.setUser(userDAO.findAppUserByUserId((long)0));
        newmu.setLink(bean.getLink());
        newmu.setUser(userDAO.findAppUserByUserName(username));
        //User curuser = UserService

        museumeDAO.save(newmu);
        return newmu;
    }

   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(Museume bean){
        museumeDAO.save(bean);
    }

    public Page<Museume> list(int cid, String country, int start, int size) {
        if(cid == -1 && country.equals("")){return listAll(start,size);}
        if(cid == -1){return this.listByCountry(country, start, size);}
        if(country.equals("")){return listByCategory(cid,start,size);}
        Category category = categoryService.get(cid);
        Pageable pageable = PageRequest.of(start, size);
        Page pagedromJPA = museumeDAO.findByCategoryAndCountry(category,country,pageable);
        return pagedromJPA;
    }

    public Page<Museume> listAll(int start, int size) {
        Pageable pageable = PageRequest.of(start, size);
        Page pagedromJPA = museumeDAO.findAll(pageable);
        return pagedromJPA;
    }

    public Page<Museume> listByCategory(int cid,int start, int size) {
        Category category = categoryService.get(cid);
        Pageable pageable = PageRequest.of(start, size);
        Page pagedromJPA = museumeDAO.findByCategory(category,pageable);
        return pagedromJPA;
    }

    public Page<Museume> listByCountry(String country,int start, int size){
        Pageable pageable = PageRequest.of(start, size);
        Page pagedromJPA = museumeDAO.findByCountry(country,pageable);
        return pagedromJPA;
    }


}
