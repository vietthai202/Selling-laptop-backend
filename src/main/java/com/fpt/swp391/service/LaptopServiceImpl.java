package com.fpt.swp391.service;

import java.util.*;

import com.fpt.swp391.dto.LaptopDto;
import com.fpt.swp391.dto.MetadataDto;
import com.fpt.swp391.model.*;
import com.fpt.swp391.repository.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class LaptopServiceImpl implements LaptopService {

    private final LaptopRepository laptopRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final UserRepository userRepository;

    public LaptopServiceImpl(LaptopRepository laptopRepository, CategoryRepository categoryRepository, BrandRepository brandRepository, UserRepository userRepository) {
        this.laptopRepository = laptopRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<LaptopDto> listAllLaptop() {
        List<Laptop> lt = laptopRepository.findAll();
        List<LaptopDto> lt1 = new ArrayList<>();
        for (Laptop laptop : lt) {
            LaptopDto laptopDto = new LaptopDto();
            laptopDto.setId(laptop.getId());
            laptopDto.setUserName(laptop.getUser().getUsername());
            laptopDto.setTitle(laptop.getTitle());
            laptopDto.setMetaTitle(laptop.getMetaTitle());
            laptopDto.setSlug(laptop.getSlug());
            laptopDto.setSummary(laptop.getSummary());
            laptopDto.setImage(laptop.getImage());
            laptopDto.setSku(laptop.getSku());
            laptopDto.setPrice(laptop.getPrice());
            laptopDto.setDiscount(laptop.getDiscount());
            laptopDto.setQuantity(laptop.getQuantity());
            laptopDto.setCategoryId(laptop.getCategory().getId());
            laptopDto.setBrandId(laptop.getBrand().getId());
            lt1.add(laptopDto);
        }
        Collections.reverse(lt1);
        return lt1;
    }

    @Override
    public Laptop findById(Long id) {
        Optional<Laptop> laptopOptional = laptopRepository.findById(id);
        if (laptopOptional.isPresent()) {
            Laptop laptop = laptopOptional.get();
            return laptop;
        }
        return null;
    }

    @Override
    public Page<LaptopDto> getProductsByFilter(String categoryName, String brandName, String sortDirection, float minPrice, float maxPrice, int pageSize, int pageNumber) {
        try {
            Specification<Laptop> specification = LaptopSpecifications.hasCategoryAndBrand(categoryName, brandName, sortDirection).and(LaptopSpecifications.hasPriceBetween(minPrice, maxPrice));
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Page<Laptop> resultPage = laptopRepository.findAll(specification, pageable);
            int totalPages = resultPage.getTotalPages();
            long totalElements = resultPage.getTotalElements();
            if (pageNumber >= totalPages && totalElements > 0) {
                pageNumber = totalPages - 1;
                pageable = PageRequest.of(pageNumber, pageSize);
                resultPage = laptopRepository.findAll(specification, pageable);
            }
            return resultPage.map(this::convertToLaptopDto);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Laptop createLaptop(LaptopDto laptop) {
        Laptop lt = new Laptop();
        lt.setTitle(laptop.getTitle());
        lt.setMetaTitle(laptop.getMetaTitle());
        Date date = new Date();
        long timestamp = date.getTime();
        lt.setSlug(laptop.getSlug() + "-" + timestamp);
        lt.setSummary(laptop.getSummary());
        lt.setImage(laptop.getImage());
        lt.setSku(timestamp + "");
        lt.setPrice(laptop.getPrice());
        lt.setDiscount(laptop.getDiscount());
        lt.setQuantity(laptop.getQuantity());
//        lt.setMetadata(laptop.getMetadata());
//        lt.setTags(laptop.getTags());

        User u = userRepository.findByUsername(laptop.getUserName());
        lt.setUser(u);
        Category c = categoryRepository.findById(laptop.getCategoryId()).orElse(null);
        lt.setCategory(c);

        Brand b = brandRepository.findById(laptop.getBrandId()).orElse(new Brand());
        lt.setBrand(b);


//        lt.setCartItems(laptop.getCartItems());
//        lt.setListFaps(laptop.getListFaps() );
//        lt.setOrderItems(laptop.getOrderItems());
//        lt.setReviews(laptop.getReviews());
        laptopRepository.save(lt);
        return lt;
    }

    @Override
    public Page<LaptopDto> getProducts(int page, int size, String sortBy, String sortOrder) {
        page = page - 1;
        try {
            if ((sortOrder.equals("asc")) || (sortOrder.equals("desc"))) {
                Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
                Pageable pageable = PageRequest.of(page, size, sort);
                Page<Laptop> productPage = laptopRepository.findAll(pageable);
                return productPage.map(this::convertToLaptopDto);
            } else {
                Pageable pageable = PageRequest.of(page, size);
                Page<Laptop> productPage = laptopRepository.findAll(pageable);
                return productPage.map(this::convertToLaptopDto);
            }

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Laptop updateLaptop(Long id, LaptopDto laptop) {
        Optional<Laptop> laptopOptional = laptopRepository.findById(id);
        if (laptopOptional.isPresent()) {
            Laptop lt = laptopOptional.get();
            lt.setId(laptop.getId());
            lt.setTitle(laptop.getTitle());
            lt.setMetaTitle(laptop.getMetaTitle());
            Date date = new Date();
            long timestamp = date.getTime();
            lt.setSlug(laptop.getSlug() + "-" + timestamp);
            lt.setSummary(laptop.getSummary());
            lt.setImage(laptop.getImage());
//            lt.setSku(laptop.getSku()); - không thay đổi SKU
            lt.setPrice(laptop.getPrice());
            lt.setDiscount(laptop.getDiscount());
            lt.setQuantity(laptop.getQuantity());
            Category c = categoryRepository.findById(laptop.getCategoryId()).orElse(new Category());
            lt.setCategory(c);
            Brand b = brandRepository.findById(laptop.getBrandId()).orElse(new Brand());
            lt.setBrand(b);
            laptopRepository.save(lt);
            return lt;
        }
        return null;
    }

    @Override
    public LaptopDto getLaptopBySlug(String slug) {
        Laptop laptop = laptopRepository.findLaptopBySlug(slug);
        if (laptop != null) {
            return convertToLaptopDto(laptop);
        }
        return null;
    }

    private LaptopDto convertToLaptopDto(Laptop laptop) {
        LaptopDto dto = new LaptopDto();
        dto.setId(laptop.getId());
        dto.setUserName(laptop.getUser().getName());
        dto.setTitle(laptop.getTitle());
        dto.setMetaTitle(laptop.getMetaTitle());
        dto.setSlug(laptop.getSlug());
        dto.setSummary(laptop.getSummary());
        dto.setImage(laptop.getImage());
        dto.setSku(laptop.getSku());
        dto.setPrice(laptop.getPrice());
        dto.setDiscount(laptop.getDiscount());
        dto.setQuantity(laptop.getQuantity());
        dto.setCategoryId(laptop.getCategory().getId());
        dto.setBrandId(laptop.getBrand().getId());

        Set<Metadata> metadataSet = laptop.getListMetadata();
        Set<MetadataDto> metadataDtoSet = new HashSet<>();
        for (Metadata meta : metadataSet) {
            MetadataDto mdto = converToMetaDataDto(meta);
            metadataDtoSet.add(mdto);
        }
        dto.setMetadataDtoSet(metadataDtoSet);
        return dto;
    }

    private MetadataDto converToMetaDataDto(Metadata metadata) {
        MetadataDto dto = new MetadataDto();
        dto.setId(metadata.getId());
        dto.setIcon(metadata.getIcon());
        dto.setIconType(metadata.getIconType());
        dto.setTitle(metadata.getTitle());
        dto.setContent(metadata.getContent());
        dto.setLaptop_id(metadata.getLaptop().getId());
        dto.setGroup_id(metadata.getMetadataGroup().getId());
        return dto;
    }
}