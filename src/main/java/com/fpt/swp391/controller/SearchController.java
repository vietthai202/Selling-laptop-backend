package com.fpt.swp391.controller;

import com.fpt.swp391.dto.SearchDto;
import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.service.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping()
    public ResponseEntity<?> getBrandById(@RequestParam String keyword) {
        List<SearchDto> lstDto = searchService.searchLaptopByTitle(keyword);
        List<SearchDto> lstDto1 = searchService.searchBogByTitle(keyword);
        List<SearchDto> result = new ArrayList<>();
        if (lstDto != null) {
            result.addAll(lstDto);
        }
        if (lstDto1 != null) {
            result.addAll(lstDto1);
        }
        if (result.size() > 0 && !keyword.trim().equals("")) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("No content", HttpStatus.NO_CONTENT, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
