package com.fpt.swp391.controller;


import com.fpt.swp391.dto.BrandDto;
import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.Brand;
import com.fpt.swp391.service.BrandService;
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
    public ResponseEntity<List<BrandDto>> getAllBrand() {
        final List<Brand> listBrand = brandService.listAllBrand();
        List<BrandDto> BrandDtos = new ArrayList<>();
        for (Brand brand : listBrand) {
            BrandDtos.add(new BrandDto(brand.getId(), brand.getName(), brand.getDescription(),brand.getImage(), brand.getSlug()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(BrandDtos);
    }
    @PostMapping("/createBrand")
    public ResponseEntity<Brand> sayHello(@RequestBody Brand brand) {
        Brand br = brandService.createBrand(brand);
        if(br != null){
            final ApiExceptionResponse response = new ApiExceptionResponse("Success!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(br);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("User not found!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @DeleteMapping("/brands/{id}")
    public ResponseEntity<ApiExceptionResponse> deleteBrand(@PathVariable Long id){
        Brand br = brandService.findById(id);
        ApiExceptionResponse response;
        if (br != null) {
            boolean isDelete = brandService.deleteBrand(br.getId());
            if(isDelete){
                response = new ApiExceptionResponse("Delete Success!", HttpStatus.OK, LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
        response = new ApiExceptionResponse("Delete Fail!", HttpStatus.OK, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @PostMapping("/brands/update/{id}")
        public ResponseEntity<ApiExceptionResponse> updateBrand(@PathVariable Long id, @RequestBody BrandDto brandDto) {
            Brand br = brandService.updateBrand(id, brandDto);
            ApiExceptionResponse response;
            if (br != null) {
                response = new ApiExceptionResponse("Update brand successfully!", HttpStatus.OK, LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            response = new ApiExceptionResponse("Update brand fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }



