package com.fpt.swp391.service;

import com.fpt.swp391.dto.CategoryDto;
import com.fpt.swp391.dto.LaptopDto;
import com.fpt.swp391.model.Category;
import com.fpt.swp391.model.Laptop;
import com.fpt.swp391.repository.CategoryRepository;
import com.fpt.swp391.repository.LaptopRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final LaptopRepository laptopRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               LaptopRepository laptopRepository) {
        this.categoryRepository = categoryRepository;
        this.laptopRepository = laptopRepository;
    }

    @Override
    public CategoryDto createCategory(Category category) {
        Category c = new Category();
        Date date = new Date();
        long timeStamp = date.getTime();
        c.setName(category.getName());
        c.setDescription(category.getDescription());
        c.setSlug(category.getSlug() + "-" + timeStamp);
        c.setImage(category.getImage());
        categoryRepository.save(c);
        CategoryDto dto = converToCategoryDTO(c);
        return dto;
    }

    @Override
    public Category findById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            return category;
        }
        return null;
    }

    @Override
    public List<Category> listAll() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean deleteCategoryById(Long id) {
        try {
            Category category = categoryRepository.findById(id).orElse(null);
            if (category.getLaptops().size() > 0) {
                Set<Laptop> laptops = category.getLaptops();
                Category category1 = categoryRepository.findCategoryByName("Uncategorized");
                if (category1 != null) {
                    for (Laptop l : laptops) {
                        l.setCategory(category1);
                        laptopRepository.save(l);
                    }
                } else {
                    Category cate = new Category();
                    cate.setName("Uncategorized");
                    cate.setDescription("Uncategorized");
                    categoryRepository.save(cate);
                    for (Laptop l : laptops) {
                        l.setCategory(cate);
                        laptopRepository.save(l);
                    }
                }
            }
            categoryRepository.delete(category);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Category updateCategoryById(Long id, Category category) {
        Optional<Category> c = categoryRepository.findById(id);
        if (c.isPresent()) {
            Category c1 = c.get();
            c1.setName(category.getName());
            c1.setDescription(category.getDescription());
            Date date = new Date();
            long timeStamp = date.getTime();
            c1.setSlug(category.getSlug() + "-" + timeStamp);
            c1.setImage(category.getImage());
            categoryRepository.save(c1);
            return c1;
        }
        return null;
    }

    @Override
    public CategoryDto getCategoryDtoBySlug(String slug) {
        try {
            Category c = categoryRepository.findCategoryBySlug(slug);
            CategoryDto c1 = converToCategoryDTO(c);
            return c1;
        } catch (Exception e) {
            return null;
        }
    }

    private CategoryDto converToCategoryDTO(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setSlug(category.getSlug());
        dto.setImage(category.getImage());
        Set<LaptopDto> laptopDTOs = new HashSet<>();
        Set<Laptop> laptops = category.getLaptops();
        if(laptops != null) {
            for (Laptop laptop : laptops) {
                LaptopDto dt = convertToLaptopDto(laptop);
                laptopDTOs.add(dt);
            }
            dto.setLaptopDtos(laptopDTOs);
        }
        return dto;
    }

    private LaptopDto convertToLaptopDto(Laptop laptop) {
        LaptopDto dto = new LaptopDto();
        dto.setId(laptop.getId());
        dto.setUserName(laptop.getUser().getName());
        dto.setTitle(laptop.getTitle());
        dto.setMetaTitle(laptop.getMetaTitle());
        dto.setSlug(laptop.getSlug());
        dto.setSummary(laptop.getSummary());
        dto.setSku(laptop.getSku());
        dto.setPrice(laptop.getPrice());

        dto.setQuantity(laptop.getQuantity());
        dto.setCategoryId(laptop.getCategory().getId());
        dto.setBrandId(laptop.getBrand().getId());
        return dto;
    }
}
