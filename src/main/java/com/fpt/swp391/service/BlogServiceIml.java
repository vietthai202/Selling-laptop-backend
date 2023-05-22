package com.fpt.swp391.service;

import com.fpt.swp391.dto.BlogDto;
import com.fpt.swp391.model.Blog;
import com.fpt.swp391.model.BlogCategory;
import com.fpt.swp391.model.User;
import com.fpt.swp391.repository.BlogCategogyRepository;
import com.fpt.swp391.repository.BlogRespository;
import com.fpt.swp391.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogServiceIml implements BlogService {
    private BlogRespository blogRespository = null;
    private BlogCategogyRepository blogCategogyRepository = null;
    private UserRepository userRepository = null;


    public BlogServiceIml(BlogRespository blogRespository, BlogCategogyRepository blogCategogyRepository,UserRepository userRepository) {
        this.blogRespository = blogRespository;
        this.blogCategogyRepository = blogCategogyRepository;
        this.userRepository = userRepository;

    }

    @Override
    public Blog createBlog(BlogDto blogDto) {
        Blog bl = new Blog();
        bl.setName(blogDto.getName());
        bl.setContent(blogDto.getContent());
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
            bl.setSlug(blog.getSlug());
            blogRespository.save(bl);
        }

        return null;
    }


}
