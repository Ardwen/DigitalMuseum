package com.example.digitalmuseum.service;


import com.example.digitalmuseum.dao.ArtImageDAO;
import com.example.digitalmuseum.model.ArtImage;
import com.example.digitalmuseum.model.ArtItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtImageService {

    public static final String type_single = "single";
    public static final String type_detail = "detail";

    @Autowired
    ArtImageDAO artImageDAO;

    public void add(ArtImage bean){
        artImageDAO.save(bean);
    }

    public void delete(int id){
        artImageDAO.deleteById(id);
    }

    public ArtImage get(int id) {
        return artImageDAO.getOne(id);
    }

    public List<ArtImage> listSingleArtImages(ArtItem artItem) {
        return artImageDAO.findByArtItemAndTypeOrderByIdDesc(artItem, type_single);
    }
    public List<ArtImage> listDetailArtImages(ArtItem artItem) {
        return artImageDAO.findByArtItemAndTypeOrderByIdDesc(artItem, type_detail);
    }

    public void setFirstArtImage(ArtItem ArtItem) {
        List<ArtImage> singleImages = listSingleArtImages(ArtItem);
        if(!singleImages.isEmpty())
            ArtItem.setFirstArtImage(singleImages.get(0));
        else
            ArtItem.setFirstArtImage(new ArtImage());
    }

    public void setFirstArtImages(List<ArtItem> ArtItems) {
        for (ArtItem ArtItem : ArtItems)
            setFirstArtImage(ArtItem);
    }


}
