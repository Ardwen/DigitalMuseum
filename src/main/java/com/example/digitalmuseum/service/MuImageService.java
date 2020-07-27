package com.example.digitalmuseum.service;


import com.example.digitalmuseum.dao.MuseumeImageDAO;
import com.example.digitalmuseum.model.Museume;
import com.example.digitalmuseum.model.MuseumeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MuImageService {
    public static final String type_single = "single";
    public static final String type_detail = "detail";

    @Autowired
    MuseumeImageDAO muImageDAO;

    public void add(MuseumeImage bean){

        muImageDAO.save(bean);
    }

    public void save(MuseumeImage bean){

        muImageDAO.save(bean);
    }

    public void delete(int id){
        muImageDAO.deleteById(id);
    }

    public MuseumeImage get(int id) {
        return muImageDAO.getOne(id);
    }

    public List<MuseumeImage> listSingleMuImages(Museume museume) {
        return muImageDAO.findByMuseumeAndTypeOrderByIdDesc(museume, type_single);
    }


    public List<MuseumeImage> listDetailArtImages(Museume museume) {
        return muImageDAO.findByMuseumeAndTypeOrderByIdDesc(museume, type_detail);
    }

    public void setFirstMuImage(Museume museume) {
        List<MuseumeImage> singleImages = listSingleMuImages(museume);
        if(!singleImages.isEmpty())
            museume.setFirstmuImage(singleImages.get(0));
        else
            museume.setFirstmuImage(new MuseumeImage());
    }

    public void setFirstMuImages(List<Museume> mus) {
        for (Museume museume : mus)
            setFirstMuImage(museume);
    }
}
