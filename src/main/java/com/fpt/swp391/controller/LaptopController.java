package com.fpt.swp391.controller;

import com.fpt.swp391.dto.DiscountDto;
import com.fpt.swp391.dto.LaptopDto;
import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.Laptop;
import com.fpt.swp391.service.DiscountService;
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
    private final DiscountService discountService;

    public LaptopController(LaptopService laptopService, DiscountService discountService) {
        this.laptopService = laptopService;
        this.discountService = discountService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<LaptopDto>> listAllLaptop() {
        final List<LaptopDto> listLt = laptopService.listAllLaptop();
        return ResponseEntity.status(HttpStatus.OK).body(listLt);
    }

    @GetMapping("/listLaptopWithStatus")
    public ResponseEntity<?> listAllLaptopWithStatus() {
        List<LaptopDto> list = laptopService.listAllLaptopWithStatus();
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("List fail", HttpStatus.NO_CONTENT, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @PutMapping("/onOffLaptop/{id}")
    public ResponseEntity<?> updateOnOffLaptop(@PathVariable Long id) {
        LaptopDto laptop = laptopService.findById(id);
        laptop.setStatus(!laptop.isStatus());
        LaptopDto lt = laptopService.updateLaptop(id, laptop);
        if (lt != null) {
            return ResponseEntity.status(HttpStatus.OK).body(lt);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Update fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/page")
    public Page<LaptopDto> getProducts(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "12") int size,
                                       @RequestParam(defaultValue = "brand") String sortBy,
                                       @RequestParam(defaultValue = "") String sortOrder) {
        return laptopService.getProducts(page, size, sortBy, sortOrder);
    }

    @GetMapping("/products")
    public ResponseEntity<Page<LaptopDto>> getProductsByBrand(
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) String listBrandId,
            @RequestParam(required = false) String sortDirection,
            @RequestParam(required = false) String priceOrder,
            @RequestParam(value = "minPrice", required = false) Float minPrice,
            @RequestParam(value = "maxPrice", required = false) Float maxPrice,
            @RequestParam(defaultValue = "6") int pageSize,
            @RequestParam(defaultValue = "1") int pageNumber) {
        if (minPrice == 0.0 || minPrice == null) minPrice = 0.0F;
        if (maxPrice == 0.0 || maxPrice == null) maxPrice = 9999999999F;
        Page<LaptopDto> laptops = laptopService.getProductsByFilter(categoryId, listBrandId, sortDirection, priceOrder, minPrice, maxPrice, pageSize, pageNumber);
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

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getLaptopById(@PathVariable Long id) {
        LaptopDto dto = laptopService.findById(id);
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
        LaptopDto lt = laptopService.updateLaptop(id, laptopDto);
        if (lt != null) {
            ApiSuccessResponse response = new ApiSuccessResponse("Update Successfully!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Update Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{laptopId}/discounts")
    public ResponseEntity<?> getDiscountsByLaptopId(@PathVariable Long laptopId) {
        List<DiscountDto> list = discountService.getDiscountsByLaptopId(laptopId);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("List fail", HttpStatus.NO_CONTENT, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @GetMapping("/discount/{discountId}")
    public ResponseEntity<List<LaptopDto>> getLaptopsByDiscountId(@PathVariable Long discountId) {
        List<LaptopDto> laptopsDto = laptopService.getLaptopsByDiscountId(discountId);
        return ResponseEntity.ok(laptopsDto);
    }

    private LaptopDto convertToDto(Laptop laptop) {
        LaptopDto laptopDto = new LaptopDto();
        laptopDto.setId(laptop.getId());
        laptopDto.setTitle(laptop.getTitle());
        laptopDto.setMetaTitle(laptop.getMetaTitle());
        laptopDto.setSlug(laptop.getSlug());
        laptopDto.setSummary(laptop.getSummary());
        laptopDto.setImage(laptop.getImage());
        laptopDto.setSku(laptop.getSku());
        laptopDto.setPrice(laptop.getPrice());
        laptopDto.setQuantity(laptop.getQuantity());
        laptopDto.setStatus(laptop.isStatus());
        return laptopDto;
    }
}
