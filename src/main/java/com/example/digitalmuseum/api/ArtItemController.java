package com.example.digitalmuseum.api;

import com.example.digitalmuseum.model.ArtImage;
import com.example.digitalmuseum.model.ArtItem;
import com.example.digitalmuseum.payload.ArtPost;
import com.example.digitalmuseum.service.ArtImageService;
import com.example.digitalmuseum.service.ArtItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ArtItemController {
    @Autowired
    ArtItemService artItemService;
    @Autowired
    ArtImageService artImageService;

    @GetMapping("/public/Museume/{mid}/arts")
    public List<ArtItem> list(@PathVariable("mid") int mid, @RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
        start = start<0?0:start;
        //Pageable pageable = new PageRequest(start, size);
        Page<ArtItem> page =artItemService.list(mid,start,size);
        artImageService.setFirstArtImages(page.getContent());
        return page.getContent();
    }

    @GetMapping("/ArtItems/{id}")
    public ArtItem get(@PathVariable("id") int id) throws Exception {
        ArtItem bean=artItemService.get(id);
        return bean;
    }

    @PostMapping("/ArtItems/add")
    public Object add(@RequestBody ArtPost bean) throws Exception {
        ArtItem createdArt = artItemService.add(bean);
        for(int i : bean.getImages()){
            ArtImage arti = artImageService.get(i);
            arti.setArtItem(createdArt);
        }
        artImageService.setFirstArtImage(createdArt);
        return createdArt;
    }

    @DeleteMapping("/ArtItems/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        artItemService.delete(id);
        return null;
    }

    @PutMapping("/ArtItems")
    public Object update(@RequestBody ArtItem bean) throws Exception {
        artItemService.update(bean);
        return bean;
    }

}
