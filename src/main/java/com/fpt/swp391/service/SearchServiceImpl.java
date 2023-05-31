package com.fpt.swp391.service;

import com.fpt.swp391.dto.SearchDto;
import com.fpt.swp391.model.Blog;
import com.fpt.swp391.model.Laptop;
import com.fpt.swp391.repository.BlogRespository;
import com.fpt.swp391.repository.LaptopRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    private final LaptopRepository laptopRepository;
    private final BlogRespository blogRespository;

    public SearchServiceImpl(LaptopRepository laptopRepository, BlogRespository blogRespository) {
        this.laptopRepository = laptopRepository;
        this.blogRespository = blogRespository;
    }

    @Override
    public List<SearchDto> searchLaptopByTitle(String keyword) {
        try {
            List<Laptop> list = laptopRepository.findByTitleContainingIgnoreCaseOrSummaryContainingIgnoreCaseOrMetaTitleContainingIgnoreCase(keyword, keyword, keyword);
            List<SearchDto> listS = new ArrayList<>();
            for (Laptop lt : list) {
                SearchDto sc = new SearchDto();
                sc.setType("laptop");
                sc.setTitle(lt.getTitle());
                sc.setSlug(lt.getSlug());
                sc.setImage(lt.getImage());
                listS.add(sc);
            }
            return listS;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public List<SearchDto> searchBogByTitle(String keyword) {
        try {
            List<Blog> list = blogRespository.findByNameContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);
            List<SearchDto> listS = new ArrayList<>();
            for (Blog lt : list) {
                SearchDto sc = new SearchDto();
                sc.setType("blog");
                sc.setTitle(lt.getName());
                sc.setSlug(lt.getSlug());
                sc.setImage(lt.getImage());
                listS.add(sc);
            }
            return listS;
        } catch (Exception e) {

        }
        return null;
    }
}
