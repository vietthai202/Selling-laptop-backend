package com.fpt.swp391.service;

import com.fpt.swp391.dto.BlogDto;
import com.fpt.swp391.model.Blog;




import java.util.List;


public interface BlogService {
    Blog createBlog(BlogDto blog);

    boolean deleteBlog(Long id);

    boolean updateBlog(Long id, BlogDto blog);

    BlogDto getBlogBySlug(String slug);

    List<BlogDto> getAllBlog();
}
