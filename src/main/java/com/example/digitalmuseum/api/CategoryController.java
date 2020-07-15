package com.example.digitalmuseum.api;


import com.example.digitalmuseum.model.Category;
import com.example.digitalmuseum.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired CategoryService categoryService;

    @CrossOrigin
    @GetMapping("/categories")
    public List<Category> list() throws Exception {
        return categoryService.list();
    }

    @PostMapping("/AddCategory")
    public Object add(@RequestBody Category category) throws Exception {
        return categoryService.add(category);
    }


}