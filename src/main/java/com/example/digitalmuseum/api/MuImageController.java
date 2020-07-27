package com.example.digitalmuseum.api;


import com.example.digitalmuseum.Util.ImageUtil;
import com.example.digitalmuseum.model.Museume;
import com.example.digitalmuseum.model.MuseumeImage;
import com.example.digitalmuseum.service.AWSService;
import com.example.digitalmuseum.service.MuImageService;
import com.example.digitalmuseum.service.MuseumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

@RestController
@CrossOrigin
public class MuImageController {

    @Autowired
    MuImageService muImageService;
    @Autowired
    MuseumeService muService;

    @Autowired
    AWSService amazonService;

    @GetMapping("/public/museume/{mid}/muImages")
    public List<MuseumeImage> list(@PathVariable("mid") int mid) throws Exception {
        Museume museume = muService.get(mid);
        List<MuseumeImage> details =  muImageService.listSingleMuImages(museume);
        return details;
    }

    @PostMapping("/AddmuImages")
    public Object add(@RequestParam("type") String type, @RequestParam("mid") int pid, @RequestParam("imageFile") MultipartFile image) throws Exception {
        MuseumeImage bean = new MuseumeImage();
        if(pid != 1) {
            Museume museume = muService.get(pid);
            bean.setMuseume(museume);
        }
        bean.setType(type);
        muImageService.add(bean);
        String fileName = "";
        if(type.equals("single")){
            fileName +="muSingle/";
        }
        else{
            fileName +="muDetail/";
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
            String f_small_name = "muSingle_small/" + id + ".jpg";
            String f_middle_name = "muSingle_middle/" + id + ".jpg";
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

    @DeleteMapping("muImages/{aid}")
    public void delete(@PathVariable("aid") int aid) throws Exception{
        MuseumeImage bean = muImageService.get(aid);
        String type = bean.getType();
        String fileName = "";
        if(type.equals("single")){
            fileName +="muSingle/";
        }
        else{
            fileName +="muDetail/";
        }
        int id = bean.getId();
        fileName += id+ ".jpg";
        amazonService.deleteFileFromS3Bucket(fileName);
        if(type.equals("single")) {
            String f_small_name = "muSingle_small/" + id + ".jpg";
            String f_middle_name = "muSingle_middle/" + id + ".jpg";
            amazonService.deleteFileFromS3Bucket(f_middle_name);
            amazonService.deleteFileFromS3Bucket(f_small_name);
        }
        muImageService.delete(aid);
    }

}