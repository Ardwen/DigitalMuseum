package com.example.digitalmuseum.api;


import com.example.digitalmuseum.Util.ImageUtil;
import com.example.digitalmuseum.model.Museume;
import com.example.digitalmuseum.model.MuseumeImage;
import com.example.digitalmuseum.service.MuImageService;
import com.example.digitalmuseum.service.MuseumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MuImageController {

    @Autowired
    MuImageService muImageService;
    @Autowired
    MuseumeService muService;

    @GetMapping("/museume/{mid}/muImages")
    public List<MuseumeImage> list(@PathVariable("mid") int mid) throws Exception {
        Museume museume = muService.get(mid);
        List<MuseumeImage> details =  muImageService.listDetailArtImages(museume);
        return details;
    }

    @PostMapping("/AddmuImages")
    public Object add(@RequestParam("imageFile") MultipartFile image,HttpServletRequest request) throws Exception {
        int pid = -1;
        String type = "detail";
        MuseumeImage bean = new MuseumeImage();
        if(pid != -1){
            Museume museume = muService.get(pid);
            bean.setMuseume(museume);
        } else {
            Museume museume = muService.get(1);
            bean.setMuseume(museume);
        }
        bean.setType(type);

        muImageService.add(bean);

        String folder = "img/";
        if(muImageService.type_single.equals(bean.getType())){
            folder +="muSingle";
        }
        else{
            folder +="muDetail";
        }

        File imageFolder= new File(request.getServletContext().getRealPath(folder));

        File file = new File(imageFolder,bean.getId()+".jpg");
        String fileName = file.getName();
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        try {
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(muImageService.type_single.equals(bean.getType())){
            String imageFolder_small= request.getServletContext().getRealPath("img/artSingle_small");
            String imageFolder_middle= request.getServletContext().getRealPath("img/artSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.getParentFile().mkdirs();
            f_middle.getParentFile().mkdirs();
            ImageUtil.resizeImage(file, 56, 56, f_small);
            ImageUtil.resizeImage(file, 217, 190, f_middle);
        }

        return bean;
    }

    @DeleteMapping("muImages/{aid}")
    public String delete(@PathVariable("aid") int aid, HttpServletRequest request) throws Exception{
        MuseumeImage bean = muImageService.get(aid);
        muImageService.delete(aid);

        String folder = "img/";
        if(muImageService.type_single.equals(bean.getType())){
            folder +="muSingle";
        }
        else{
            folder +="muDetail";
        }
        File imageFolder= new File(request.getServletContext().getRealPath(folder));
        File file = new File(imageFolder,bean.getId()+".jpg");
        String fileName = file.getName();
        file.delete();

        if(muImageService.type_single.equals(bean.getType())) {
            String imageFolder_small = request.getServletContext().getRealPath("img/artSingle_small");
            String imageFolder_middle = request.getServletContext().getRealPath("img/artSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.delete();
            f_middle.delete();
        }
        return null;
    }

}