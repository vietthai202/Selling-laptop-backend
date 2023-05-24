package com.fpt.swp391.service;

import com.fpt.swp391.dto.BlogDto;
import com.fpt.swp391.model.Blog;
import com.fpt.swp391.model.BlogCategory;
import com.fpt.swp391.model.User;
import com.fpt.swp391.repository.BlogCategogyRepository;
import com.fpt.swp391.repository.BlogRespository;
import com.fpt.swp391.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BlogServiceIml implements BlogService {
    private final BlogRespository blogRespository;
    private final BlogCategogyRepository blogCategogyRepository;
    private final UserRepository userRepository;

    public BlogServiceIml(BlogRespository blogRespository, BlogCategogyRepository blogCategogyRepository, UserRepository userRepository) {
        this.blogRespository = blogRespository;
        this.blogCategogyRepository = blogCategogyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Blog createBlog(BlogDto blogDto) {
        Blog bl = new Blog();
        bl.setName(blogDto.getName());
        bl.setContent(blogDto.getContent());
        Date date = new Date();
        long timestamp = date.getTime();
        bl.setSlug(blogDto.getSlug() + "-" + timestamp);
        bl.setImage(blogDto.getImage());
        bl.setCreatedAt(new Date());
        bl.setShortContent(blogDto.getShortContent());
        BlogCategory bc = blogCategogyRepository.findById(blogDto.getCategoryId()).orElse(new BlogCategory());
        bl.setBlogCategory(bc);
        User u = userRepository.findByUsername(blogDto.getUserName());
        bl.setUser(u);
        blogRespository.save(bl);
        return bl;
    }

    @Override
    public boolean deleteBlog(Long id) {
        Optional<Blog> blogOp = blogRespository.findById(id);
        if (blogOp.isPresent()) {
            Blog bc = blogOp.get();
            blogRespository.delete(bc);
            return true;
        }
        return false;
    }

    @Override
    public Blog updateBlog(Long id, BlogDto blog) {
        Optional<Blog> blogOp = blogRespository.findById(id);
        if (blogOp.isPresent()) {
            Blog bl = blogOp.get();
            bl.setName(blog.getName());
            bl.setContent(blog.getContent());
            bl.setImage(blog.getImage());
            bl.setCreatedAt(blog.getCreatedAt());
            bl.setShortContent(blog.getShortContent());
            Date date = new Date();
            long timestamp = date.getTime();
            bl.setSlug(blog.getSlug() + "-" + timestamp);
            blogRespository.save(bl);
        }

        return null;
    }

    @Override
    public BlogDto getBlogBySlug(String slug) {
        try {
            Blog blog = blogRespository.findBlogBySlug(slug);
            BlogDto blogDto = blogDtoConvert(blog);
            return blogDto;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<BlogDto> getAllBlog() {
        try {
            List<Blog> blogs = blogRespository.findAll();
            List<BlogDto> blogDtoList = new ArrayList<>();
            for (Blog blog: blogs) {
                BlogDto blogDto = blogDtoConvert(blog);
                blogDtoList.add(blogDto);
            }
            Collections.reverse(blogDtoList);
            return blogDtoList;
        } catch (Exception e) {
            return null;
        }
    }

    private BlogDto blogDtoConvert(Blog blog) {
        BlogDto blogDto = new BlogDto();
        blogDto.setId(blog.getId());
        blogDto.setUserName(blog.getUser().getUsername());
        blogDto.setName(blog.getName());
        blogDto.setContent(blog.getContent());
        blogDto.setImage(blog.getImage());
        blogDto.setCreatedAt(blog.getCreatedAt());
        blogDto.setShortContent(blog.getShortContent());
        blogDto.setSlug(blog.getSlug());
        blogDto.setCategoryId(blog.getBlogCategory().getId());
        return blogDto;
    }
}
