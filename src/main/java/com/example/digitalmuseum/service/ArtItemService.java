package com.example.digitalmuseum.service;


import com.example.digitalmuseum.dao.ArtItemDAO;
import com.example.digitalmuseum.model.ArtItem;
import com.example.digitalmuseum.model.Museume;
import com.example.digitalmuseum.payload.ArtPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.management.MemoryUsage;
import java.util.List;

@Service
public class ArtItemService {
    @Autowired
    ArtItemDAO artItemDAO;

    @Autowired
    MuseumeService museumeService;

    public ArtItem get(int id){ return artItemDAO.getOne(id);}

    public ArtItem add(ArtPost bean){
        System.out.println(bean.getMuseumeId());
        Museume mu = museumeService.get(bean.getMuseumeId());
        ArtItem artItem = new ArtItem();
        artItem.setIntro(bean.getDescription());
        artItem.setName(bean.getName());
        artItem.setMuseume(mu);
        return artItemDAO.save(artItem);
    }

    public void delete(int id){
        artItemDAO.deleteById(id);
    }

    public void update(ArtItem bean) {
        artItemDAO.save(bean);
    }

    public Page list(int mid, int start, int size) {
        Museume museume = museumeService.get(mid);
        Sort sort =  Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<ArtItem> pageFromJPA =artItemDAO.findByMuseume(museume,pageable);
        return pageFromJPA;
    }

    public List<ArtItem> listByMuseume(Museume museume){
        return artItemDAO.findByMuseume(museume);
    }
}
