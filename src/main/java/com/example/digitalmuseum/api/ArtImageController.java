package com.example.digitalmuseum.api;


import com.example.digitalmuseum.Util.ImageUtil;
import com.example.digitalmuseum.model.ArtImage;
import com.example.digitalmuseum.model.ArtItem;
import com.example.digitalmuseum.service.AWSService;
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

    @Autowired
    AWSService amazonService;

    @GetMapping("/public/arts/{aid}/artImages")
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

    @PostMapping("/artImages/add")
    public Object add(@RequestParam("aid") int pid, @RequestParam("type") String type, @RequestParam("imageFile") MultipartFile image, HttpServletRequest request) throws Exception {
        ArtImage bean = new ArtImage();
        if(pid != 1) {
            ArtItem artItem = artItemService.get(pid);
            bean.setArtItem(artItem);
        }
        bean.setType(type);
        artImageService.add(bean);
        String fileName = "";
        if(type.equals("single")){
            fileName +="artSingle/";
        }
        else{
            fileName +="artDetail/";
        }
        int id = bean.getId();
        fileName += id+ ".jpg";
        File file = ImageUtil.convertMultipartToFile(image);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img,"jpg",file);
        amazonService.uploadFile(file,fileName);
        System.out.println(fileName);
        if(type.equals("single")){
            File f_small = new File(file.getName());
            File f_middle = new File(file.getName());
            String f_small_name = "artSingle_small/" + id + ".jpg";
            String f_middle_name = "artSingle_middle/" + id + ".jpg";
            ImageUtil.resizeImage(file, 300, 200, f_middle);
            amazonService.uploadFile(f_small,f_small_name);
            ImageUtil.resizeImage(file, 90, 60, f_small);
            amazonService.uploadFile(f_middle,f_middle_name);
            f_small.delete();
            f_middle.delete();
        }
        file.delete();
        return bean;

    }

    @DeleteMapping("/artImages/{aid}")
    public String delete(@PathVariable("aid") int aid, HttpServletRequest request) throws Exception{
        ArtImage bean = artImageService.get(aid);
        String type = bean.getType();
        String fileName = "";
        if(type.equals("single")){
            fileName +="artSingle/";
        }
        else{
            fileName +="artDetail/";
        }
        int id = bean.getId();
        fileName += id+ ".jpg";
        amazonService.deleteFileFromS3Bucket(fileName);
        if(type.equals("single")) {
            String f_small_name = "artSingle_small/" + id + ".jpg";
            String f_middle_name = "artSingle_middle/" + id + ".jpg";
            amazonService.deleteFileFromS3Bucket(f_middle_name);
            amazonService.deleteFileFromS3Bucket(f_small_name);
        }
        artImageService.delete(aid);
        return "sucess";
    }

}
