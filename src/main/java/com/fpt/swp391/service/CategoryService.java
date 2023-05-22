package com.fpt.swp391.service;

import com.fpt.swp391.model.Category;
import java.util.List;
public interface CategoryService {
     Category createCategory(Category category);

     Category findById(Long id);

     List<Category> listAll();

     boolean deleteCategoryById(Long id);

     Category updateCategoryById(Long id, Category category);
}
