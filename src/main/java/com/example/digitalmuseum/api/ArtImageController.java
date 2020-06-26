package com.example.digitalmuseum.api;


import com.example.digitalmuseum.Util.ImageUtil;
import com.example.digitalmuseum.model.ArtImage;
import com.example.digitalmuseum.model.ArtItem;
import com.example.digitalmuseum.service.ArtImageService;
import com.example.digitalmuseum.service.ArtItemService;
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
public class ArtImageController {
    @Autowired
    ArtImageService artImageService;
    ArtItemService artItemService;

    @GetMapping("/arts/{aid}/artImages")
    public List<ArtImage> list(@RequestParam("type") String type, @PathVariable("aid") int pid) throws Exception {
        ArtItem artitem = artItemService.get(pid);

        if(artImageService.type_single.equals(type)) {
            List<ArtImage> singles =  artImageService.listSingleArtImages(artitem);
            return singles;
        }
        else if(artImageService.type_detail.equals(type)) {
            List<ArtImage> details =  artImageService.listDetailArtImages(artitem);
            return details;
        }
        else {
            return new ArrayList<>();
        }
    }

    @PostMapping("/artImages")
    public Object add(@RequestParam("aid") int pid, @RequestParam("type") String type, MultipartFile image, HttpServletRequest request) throws Exception {
        ArtImage bean = new ArtImage();
        ArtItem artItem = artItemService.get(pid);
        bean.setArtItem(artItem);
        bean.setType(type);

        artImageService.add(bean);
        String folder = "img/";
        if(artImageService.type_single.equals(bean.getType())){
            folder +="artSingle";
        }
        else{
            folder +="artDetail";
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

        if(artImageService.type_single.equals(bean.getType())){
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

    @DeleteMapping("artImages/{aid}")
    public String delete(@PathVariable("aid") int aid, HttpServletRequest request) throws Exception{
        ArtImage bean = artImageService.get(aid);
        artItemService.delete(aid);

        String folder = "img/";
        if(artImageService.type_single.equals(bean.getType())){
            folder +="artSingle";
        }
        else{
            folder +="artDetail";
        }
        File imageFolder= new File(request.getServletContext().getRealPath(folder));
        File file = new File(imageFolder,bean.getId()+".jpg");
        String fileName = file.getName();
        file.delete();

        if(artImageService.type_single.equals(bean.getType())) {
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
