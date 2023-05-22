package com.fpt.swp391.service;

import com.fpt.swp391.model.BlogCategory;
import com.fpt.swp391.model.Brand;
import com.fpt.swp391.repository.BlogCategogyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor

public class BlogCategoryServiceIml implements BlogCategoryService {
    public final BlogCategogyRepository blogCategogyRepository;


    @Override
    public BlogCategory createBC(BlogCategory blogCategory) {
        BlogCategory bc = new BlogCategory();
        bc.setName(blogCategory.getName());
        bc.setContent(blogCategory.getContent());
        blogCategogyRepository.save(bc);
        return bc;

    }

    @Override
    public   Boolean deleteBC(Long id)  {
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
        public BlogCategory updateBC (Long id, BlogCategory blogCategory){
            Optional<BlogCategory> updateOp = blogCategogyRepository.findById(id);
            if(updateOp.isPresent()){
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
        public List<BlogCategory> findAllId() {
            return blogCategogyRepository.findAll();
        }



}
