package com.fpt.swp391.service;

import com.fpt.swp391.dto.BlogCategoryDto;
import com.fpt.swp391.dto.BlogDto;
import com.fpt.swp391.model.Blog;
import com.fpt.swp391.model.BlogCategory;
import com.fpt.swp391.repository.BlogCategogyRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BlogCategoryServiceIml implements BlogCategoryService {
    public final BlogCategogyRepository blogCategogyRepository;

    public BlogCategoryServiceIml(BlogCategogyRepository blogCategogyRepository) {
        this.blogCategogyRepository = blogCategogyRepository;
    }

    @Override
    public BlogCategoryDto createBC(BlogCategory blogCategory) {
        BlogCategory bc = new BlogCategory();
        bc.setName(blogCategory.getName());
        bc.setContent(blogCategory.getContent());
        blogCategogyRepository.save(bc);
        BlogCategoryDto dto = convertToCategoryDTO(bc);
        return dto;
    }

    @Override
    public Boolean deleteBC(Long id) {
        Optional<BlogCategory> blogCategoryOp = blogCategogyRepository.findById(id);
        if (blogCategoryOp.isPresent()) {
            BlogCategory bc = blogCategoryOp.get();
            blogCategogyRepository.delete(bc);
            return true;

        }
        return null;
    }

    @Override
    public BlogCategory findById(Long id) {
        Optional<BlogCategory> BlogcategoryOp = blogCategogyRepository.findById(id);
        if (BlogcategoryOp.isPresent()) {
            BlogCategory bc = BlogcategoryOp.get();
            return bc;
        }
        return null;
    }

    @Override
    public BlogCategory updateBC(Long id, BlogCategory blogCategory) {
        Optional<BlogCategory> updateOp = blogCategogyRepository.findById(id);
        if (updateOp.isPresent()) {
            BlogCategory bc = updateOp.get();
            bc.setName(blogCategory.getName());
            bc.setBlogs(blogCategory.getBlogs());
            bc.setContent(blogCategory.getContent());
            blogCategogyRepository.save(bc);
            return bc;
        }
        return null;
    }

    @Override
    public List<BlogCategoryDto> listAllBlogCategory() {
        try {
            List<BlogCategoryDto> blogCategoryDtoList = new ArrayList<>();
            List<BlogCategory> blogCategoryList = blogCategogyRepository.findAll();
            for (BlogCategory b: blogCategoryList) {
                blogCategoryDtoList.add(convertToCategoryDTO(b));
            }
            return blogCategoryDtoList;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public BlogCategoryDto getBlogCategoryDtoById(Long id) {
        BlogCategory bc = blogCategogyRepository.findById(id).orElse(null);
        if(bc != null){
            BlogCategoryDto blogCategoryDto = convertToCategoryDTO(bc);
            return blogCategoryDto;
        }
        return null;
    }

    private BlogCategoryDto convertToCategoryDTO(BlogCategory category) {
        BlogCategoryDto dto = new BlogCategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setContent(category.getContent());
        Set<BlogDto> blogDTOs = new HashSet<>();
//        Set<Blog> blogs = category.getBlogs();
//        for (Blog blog: blogs) {
//            blogDTOs.add(convertToBlogDto(blog));
//        }
        dto.setBlogDtos(blogDTOs);
        return dto;
    }

    private BlogDto convertToBlogDto(Blog blog) {
        BlogDto dto = new BlogDto();
        dto.setId(blog.getId());
        dto.setUserName(blog.getUser().getName());
        dto.setName(blog.getName());
        dto.setContent(blog.getContent());
        dto.setImage(blog.getImage());
        dto.setCreatedAt(blog.getCreatedAt());
        dto.setShortContent(blog.getShortContent());
        dto.setSlug(blog.getSlug());
        dto.setCategoryId(blog.getBlogCategory().getId());
        return dto;
    }

}
