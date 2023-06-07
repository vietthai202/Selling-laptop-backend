package com.fpt.swp391.controller;

import com.fpt.swp391.dto.LaptopDto;
import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.Laptop;
import com.fpt.swp391.service.LaptopService;
import com.fpt.swp391.utils.ApiSuccessResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/laptop")
public class LaptopController {
    private final LaptopService laptopService;

    public LaptopController(LaptopService laptopService) {
        this.laptopService = laptopService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<LaptopDto>> listAllLaptop() {
        final List<LaptopDto> listLt = laptopService.listAllLaptop();
        return ResponseEntity.status(HttpStatus.OK).body(listLt);
    }

    @GetMapping("/page")
    public Page<LaptopDto> getProducts( @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "12") int size,
                                        @RequestParam(defaultValue = "brand") String sortBy,
                                        @RequestParam(defaultValue = "")String sortOrder) {
        return  laptopService.getProducts(page, size, sortBy, sortOrder);
    }

    @GetMapping("/products")
    public ResponseEntity<Page<LaptopDto>> getProductsByBrand(
                                        @RequestParam(required = false) String categoryName,
                                        @RequestParam(required = false) String brandName,
                                        @RequestParam(defaultValue = "asc") String sortDirection,
                                        @RequestParam(value = "minPrice", required = false) Float minPrice,
                                        @RequestParam(value = "maxPrice", required = false) Float maxPrice,
                                        @RequestParam(defaultValue = "10") int pageSize,
                                        @RequestParam(defaultValue = "0") int pageNumber) {
        if(minPrice==null) minPrice= 0.0F;
        if(maxPrice==null) maxPrice= 9999999999F;
        Page<LaptopDto> laptops = laptopService.getProductsByFilter(categoryName, brandName, sortDirection, minPrice, maxPrice, pageSize, pageNumber);
        return ResponseEntity.ok(laptops);
    }

    @GetMapping("/get/{slug}")
    public ResponseEntity<?> getLaptopBySlug(@PathVariable String slug) {
        LaptopDto dto = laptopService.getLaptopBySlug(slug);
        if (dto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Can't get by slug!", HttpStatus.NO_CONTENT, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLaptop(@RequestBody LaptopDto laptopDto) {
        Laptop lt = laptopService.createLaptop(laptopDto);
        if (lt != null) {
            Long laptopId = lt.getId();
            return ResponseEntity.status(HttpStatus.OK).body(laptopId);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Create Fail", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateLaptop(@PathVariable Long id, @RequestBody LaptopDto laptopDto) {
        Laptop lt = laptopService.updateLaptop(id, laptopDto);
        if (lt != null) {
            ApiSuccessResponse response = new ApiSuccessResponse("Update Successfully!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Update Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
