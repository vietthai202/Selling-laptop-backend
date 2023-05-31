package com.fpt.swp391.service;

import java.util.List;

import com.fpt.swp391.dto.LaptopDto;
import com.fpt.swp391.model.Laptop;
import org.springframework.data.domain.Page;

public interface LaptopService {

    List<LaptopDto> listAllLaptop();
    Laptop findById(Long id);

    Page<LaptopDto> getProductsByFilter(String categoryName,String brandName, String sortDirection, float minPrice, float maxPrice, int pageSize, int pageNumber);

    Laptop createLaptop(LaptopDto laptopDto);

    Page<LaptopDto> getProducts(int page, int size, String sortBy, String sortOrder);

    Laptop updateLaptop(Long id, LaptopDto laptop);

    LaptopDto getLaptopBySlug(String slug);
}
