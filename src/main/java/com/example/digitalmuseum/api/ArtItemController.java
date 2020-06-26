package com.example.digitalmuseum.api;

import com.example.digitalmuseum.model.ArtItem;
import com.example.digitalmuseum.service.ArtImageService;
import com.example.digitalmuseum.service.ArtItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ArtItemController {
    @Autowired
    ArtItemService artItemService;
    @Autowired
    ArtImageService artImageService;

    @GetMapping("museums/{mid}/arts")
    public Page list(@PathVariable("mid") int mid, @RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;
        //Pageable pageable = new PageRequest(start, size);
        Page<ArtItem> page =artItemService.list(mid,start,size);
        artImageService.setFirstArtImages(page.getContent());
        return page;
    }

    @GetMapping("/ArtItems/{id}")
    public ArtItem get(@PathVariable("id") int id) throws Exception {
        ArtItem bean=artItemService.get(id);
        return bean;
    }

    @PostMapping("/ArtItems")
    public Object add(@RequestBody ArtItem bean) throws Exception {
        artItemService.add(bean);
        return bean;
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
