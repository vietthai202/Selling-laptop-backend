package com.fpt.swp391.service;

import com.fpt.swp391.dto.BrandDto;
import com.fpt.swp391.model.Brand;

import java.util.List;

public interface BrandService {
    Brand createBrand(Brand brand);

    Brand findById(Long id);

    List<Brand> listAllBrand();

    boolean deleteBrand(Long id);

    Brand updateBrand(Long id, BrandDto brandDto);

    BrandDto findBrandDtoById(Long id);
}
