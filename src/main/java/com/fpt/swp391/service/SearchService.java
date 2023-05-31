package com.fpt.swp391.service;

import com.fpt.swp391.dto.SearchDto;

import java.util.List;

public interface SearchService {
    List<SearchDto> searchLaptopByTitle(String keyword);

    List<SearchDto> searchBogByTitle(String keyword);
}
