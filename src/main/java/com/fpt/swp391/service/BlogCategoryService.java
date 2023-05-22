package com.fpt.swp391.service;


import com.fpt.swp391.model.BlogCategory;
import com.fpt.swp391.model.Brand;

import java.util.List;

public interface BlogCategoryService {
   BlogCategory createBC(BlogCategory blogCategory);
   Boolean  deleteBC(Long id);
    BlogCategory findById(Long id);
   BlogCategory updateBC(Long id, BlogCategory blogCategory);
    List<BlogCategory> findAllId();

    }

