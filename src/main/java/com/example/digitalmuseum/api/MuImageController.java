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
@CrossOrigin
public class MuImageController {

    @Autowired
    MuImageService muImageService;
    @Autowired
    MuseumeService muService;

    @GetMapping("/museume/{mid}/muImages")
    public List<MuseumeImage> list(@PathVariable("mid") int mid) throws Exception {
        Museume museume = muService.get(mid);
        List<MuseumeImage> details =  muImageService.listSingleMuImages(museume);
        return details;
    }

    @PostMapping("/AddmuImages")
    public Object add(@RequestParam("mid") int pid, @RequestParam("imageFile") MultipartFile image,HttpServletRequest request) throws Exception {
        String type = "single";
        MuseumeImage bean = new MuseumeImage();
        Museume museume = muService.get(pid);
        bean.setMuseume(museume);
        bean.setType(type);

        muImageService.add(bean);

        String folder = "/Users/apple/Downloads/digitalmuseum/img/";
        if(muImageService.type_single.equals(bean.getType())){
            folder +="muSingle";
        }
        else{
            folder +="muDetail";
        }

        File imageFolder= new File(folder);


        File file = new File(imageFolder,bean.getId()+".jpg");
        String fileName = bean.getId()+".jpg";
        String toprint = file.getAbsolutePath();
        System.out.println(toprint);
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
            String imageFolder_small= "img/muSingle_small";
            String imageFolder_middle= "img/muSingle_middle";
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
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
            String imageFolder_small = request.getServletContext().getRealPath("img/muSingle_small");
            String imageFolder_middle = request.getServletContext().getRealPath("img/muSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.delete();
            f_middle.delete();
        }
        return null;
    }

}