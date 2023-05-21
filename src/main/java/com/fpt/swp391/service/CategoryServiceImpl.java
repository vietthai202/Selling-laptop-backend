package com.fpt.swp391.service;

import com.fpt.swp391.model.Category;
import com.fpt.swp391.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(Category category) {
        Category c = new Category();
        c.setName(category.getName());
        c.setSlug(category.getSlug());
        c.setImage(category.getImage());
        categoryRepository.save(c);
        return c;
    }

    @Override
    public boolean deleteCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category != null){
            categoryRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Category updateCategoryById(Long id, Category category) {
        Optional<Category> c = categoryRepository.findById(id);
        if(c.isPresent()){
            Category c1 = c.get();
            c1.setName(category.getName());
            c1.setSlug(category.getSlug());
            c1.setImage(category.getImage());
            categoryRepository.save(c1);
            return c1;
        }
        return null;
    }

}
