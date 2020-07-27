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

import java.util.ArrayList;
import java.util.List;

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
    public void update(MuPost bean, int id){
        Museume newmu = this.get(id);
        newmu.setName(bean.getTitle());
        newmu.setCountry(bean.getCountry());
        newmu.setCity(bean.getCity());
        newmu.setIntroduction(bean.getDescription());
        newmu.setCategory(categoryService.get(bean.getCategory()));
        newmu.setLink(bean.getLink());
        museumeDAO.save(newmu);
    }

    public List<Museume> list(List<String> cid, String country) {
        if(cid.size() == 0 && country.equals("")){return listAll();}
        if(cid.size() == 0){return this.listByCountry(country);}
        if(country.equals("")){return listByCategory(cid);}
        List<Museume> result = new ArrayList<>();
        for(String id: cid){
            Category category = categoryService.get(Integer.parseInt(id));
            result.addAll(museumeDAO.findByCategoryAndCountry(category,country));
        }

        return result;
    }

    public List<Museume> listAll() {
        return museumeDAO.findAll();
    }

    public List<Museume> listByCategory(List<String> cid) {
        List<Museume> result = new ArrayList<>();
        for(String id : cid){
            Category category = categoryService.get(Integer.parseInt(id));
            result.addAll(museumeDAO.findByCategory(category));
        }

        return result;
    }

    public List<Museume> listByUser(String username){
        User user = userDAO.findAppUserByUserName(username);
        return museumeDAO.findByUser(user);
    }

    public List<Museume> listByCountry(String country){
        return museumeDAO.findByCountry(country);
    }

    public void delete(int mid){
        museumeDAO.deleteById(mid);
    }

}
