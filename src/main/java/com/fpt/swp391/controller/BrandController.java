package com.fpt.swp391.controller;

import com.fpt.swp391.dto.BrandDto;
import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.Brand;
import com.fpt.swp391.service.BrandService;
import com.fpt.swp391.utils.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController

public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/brands")
    public ResponseEntity<List<Brand>> getAllBrand() {
        final List<Brand> listBrand = brandService.listAllBrand();
        List<Brand> brandd = new ArrayList<>();
        for (Brand brand : listBrand) {
                Brand b = new Brand();
                b.setId(brand.getId());
                b.setName(brand.getName());
                b.setImage(brand.getImage());
                b.setSlug(brand.getSlug());
                b.setDescription(brand.getDescription());
                brandd.add(b);
        }
        return ResponseEntity.status(HttpStatus.OK).body(brandd);
    }

    @PostMapping("/brand")
    public ResponseEntity<?> createBrand(@RequestBody Brand brand) {
        Brand br = brandService.createBrand(brand);
        if (br != null) {
            ApiSuccessResponse response = new ApiSuccessResponse("Create Successfully!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Create Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @DeleteMapping("/brands/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable Long id) {
        Brand br = brandService.findById(id);
        if (br != null) {
            boolean isDelete = brandService.deleteBrand(br.getId());
            if (isDelete) {
                ApiSuccessResponse response = new ApiSuccessResponse("Delete Successfully!", HttpStatus.OK, LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Delete Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/brands/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable Long id, @RequestBody BrandDto brandDto) {
        Brand br = brandService.updateBrand(id, brandDto);
        if (br != null) {
            ApiSuccessResponse response = new ApiSuccessResponse("Update Successfully!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Update Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/brands/{id}")
    public ResponseEntity<?> getBrandById(@PathVariable Long id) {
        BrandDto br = brandService.findBrandDtoById(id);
        if (br != null) {
            return ResponseEntity.status(HttpStatus.OK).body(br);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Update Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/category/{slug}")
    public ResponseEntity<BrandDto> getBrandBySlug(@PathVariable String slug) {
        BrandDto categoryDto = brandService.getBrandDtoBySlug(slug);
        if (categoryDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}



