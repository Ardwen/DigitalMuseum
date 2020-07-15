package com.example.digitalmuseum.service;


import com.example.digitalmuseum.dao.CategoryDAO;
import com.example.digitalmuseum.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryDAO categoryDAO;
    public List<Category>list() {
        Sort sort =  Sort.by(Sort.Direction.DESC,"id");
        return categoryDAO.findAll(sort);
    }

    public Category get(int cid){
        return categoryDAO.getOne(cid);
    }

    public Object add(Category bean) {
        return categoryDAO.save(bean);
    }
}
