package com.example.digitalmuseum.service;


import com.example.digitalmuseum.dao.ArtItemDAO;
import com.example.digitalmuseum.model.ArtItem;
import com.example.digitalmuseum.model.Museume;
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

    public ArtItem get(int id){ return artItemDAO.getOne(id);}

    public void add(ArtItem bean){
        artItemDAO.save(bean);
    }

    public void delete(int id){
        artItemDAO.deleteById(id);
    }

    public void update(ArtItem bean) {
        artItemDAO.save(bean);
    }

    public Page list(int mid, int start, int size) {
        Museume museume = MuseumeService.get(mid);
        Sort sort =  Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<ArtItem> pageFromJPA =artItemDAO.findByMuseum(museume,pageable);
        return pageFromJPA;
    }

    public List<ArtItem> listByMuseume(Museume museume){
        artItemDAO.findByMuseumeAndArtItemIsNull(museume);
    }
}
