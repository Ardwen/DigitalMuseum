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
    public Object add(@PathVariable("username") String username, @RequestBody MuPost bean, HttpServletRequest request) throws Exception {
        Museume museume = museumeService.add(bean,username);

        List<Integer> mui = bean.getImages();
        for(int i: mui){
            System.out.println(i);
            MuseumeImage muimage = muImageService.get(i);
            muimage.setMuseume(museume);
            muImageService.save(muimage);
        }

        return museume;
    }


    @PostMapping("/public/getMuseum")
    public List<Museume> list(@RequestBody MuRequest var) throws Exception{
        List<Museume> all =museumeService.list(var.getCid(),var.getCountry());
        muImageService.setFirstMuImages(all);
        return all;
    }

    @GetMapping("/Museume/{id}")
    public Museume get(@PathVariable("id") int id) throws Exception {
        Museume bean=museumeService.get(id);
        muImageService.setFirstMuImage(bean);
        return bean;
    }

    @GetMapping("/ListMuseume/{username}")
    public List<Museume> getByUser(@PathVariable("username") String username) throws Exception {
        List<Museume> all =museumeService.listByUser(username);
        muImageService.setFirstMuImages(all);
        return all;
    }

    @PostMapping("/EditMuseume/{mid}")
    //@PreAuthorize(value="hasRole('ROLE_ADMIN')")
    public Object add(@PathVariable("mid") int mid, @RequestBody MuPost bean) throws Exception {
        museumeService.update(bean,mid);
        return "success";
    }

    @DeleteMapping("/deleteMuseume/{mid}")
    public String delete(@PathVariable("mid") int mid) throws Exception{
        museumeService.delete(mid);
        return "sucess";
    }

}
