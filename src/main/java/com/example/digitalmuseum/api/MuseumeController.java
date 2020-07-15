package com.example.digitalmuseum.api;


import com.example.digitalmuseum.Util.ImageUtil;
import com.example.digitalmuseum.dao.MuseumeImageDAO;
import com.example.digitalmuseum.model.ArtItem;
import com.example.digitalmuseum.model.Museume;
import com.example.digitalmuseum.model.MuseumeImage;
import com.example.digitalmuseum.payload.MuPost;
import com.example.digitalmuseum.payload.MuRequest;
import com.example.digitalmuseum.service.MuImageService;
import com.example.digitalmuseum.service.MuseumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class MuseumeController {
    @Autowired
    MuseumeService museumeService;

    @Autowired
    MuImageService muImageService;

    @Autowired
    MuseumeImageDAO museumeImageDAO;

    @CrossOrigin
    @PostMapping("/AddMuseume/{username}")
    //@PreAuthorize(value="hasRole('ROLE_ADMIN')")
    public Object add(@PathVariable("username") String username, @RequestBody MuPost bean, HttpServletRequest request) throws Exception {
        Museume museume = museumeService.add(bean,username);

        List<Integer> mu = bean.getImages();
        for(int i: mu){
            MuseumeImage muimage = muImageService.get(i);
            muimage.setMuseume(museume);
        }

        return museume;
    }


    @GetMapping("/getMuseum")
    public Page list(@RequestBody MuRequest var) throws Exception{
        Page<Museume> page =museumeService.list(var.getCid(),var.getCountry(),var.getSkip(),var.getLimit());
        muImageService.setFirstMuImages(page.getContent());
        return page;
    }

    @GetMapping("/Museume/{id}")
    public Museume get(@PathVariable("id") int id) throws Exception {
        Museume bean=museumeService.get(id);
        muImageService.setFirstMuImage(bean);
        return bean;
    }


}
