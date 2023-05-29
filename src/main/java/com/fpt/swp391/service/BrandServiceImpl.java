package com.fpt.swp391.service;

import com.fpt.swp391.dto.BrandDto;
import com.fpt.swp391.dto.LaptopDto;
import com.fpt.swp391.model.Brand;
import com.fpt.swp391.model.Laptop;
import com.fpt.swp391.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class BrandServiceImpl implements BrandService {
    private BrandRepository brandRepository = null;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand createBrand(Brand brand) {
        Brand br = new Brand();
        br.setName(brand.getName());
        br.setDescription(brand.getDescription());
        br.setImage(brand.getImage());
        Date date = new Date();
        long timestamp = date.getTime();
        br.setSlug(brand.getSlug() + "-"+ timestamp);
        brandRepository.save(br);
        return br;
    }

    @Override
    public Brand findById(Long id) {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isPresent()) {
            Brand brand = brandOptional.get();
            return brand;
        }
        return null;
    }

    @Override
    public List<Brand> listAllBrand() {
        return brandRepository.findAll();
    }

    @Override
    public boolean deleteBrand(Long id) {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isPresent()) {
            Brand brand = brandOptional.get();
            brandRepository.delete(brand);
            return true;
        }
        return false;
    }

    @Override
    public Brand updateBrand(Long id, BrandDto brandDto) {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isPresent()) {
            Brand brand = brandOptional.get();
            brand.setName(brandDto.getName());
            brand.setDescription(brandDto.getDescription());
            brand.setImage(brandDto.getImage());
            Date date = new Date();
            long timestamp = date.getTime();
            brand.setSlug(brandDto.getSlug() + "-" + timestamp);
            brandRepository.save(brand);
            return brand;
        }
        return null;
    }

    @Override
    public BrandDto findBrandDtoById(Long id) {
        Brand b = brandRepository.findById(id).orElse(null);
        if(b != null) {
            BrandDto dto = new BrandDto();
            dto.setId(b.getId());
            dto.setName(b.getName());
            dto.setDescription(b.getDescription());
            dto.setImage(b.getImage());
            dto.setSlug(b.getSlug());
            return dto;
        }
        return null;
    }

    @Override
    public BrandDto getBrandDtoBySlug(String slug) {
        try {
            Brand b = brandRepository.findBrandBySlug(slug);
            BrandDto c1 = converToBrandDTO(b);
            return c1;
        } catch (Exception e) {
            return null;
        }
    }

    private BrandDto converToBrandDTO(Brand brand){
        BrandDto dto = new BrandDto();
        dto.setName(brand.getName());
        dto.setSlug(brand.getSlug());
        dto.setImage(brand.getImage());
        Set<LaptopDto> laptopDTOs = new HashSet<>();
        Set<Laptop> laptops = brand.getLaptops();
        for (Laptop laptop : laptops) {
            LaptopDto dt = convertToLaptopDto(laptop);
            laptopDTOs.add(dt);
        }
        dto.setLaptopDtos(laptopDTOs);
        return dto;

    }

    private LaptopDto convertToLaptopDto(Laptop laptop){
        LaptopDto dto = new LaptopDto();
        dto.setId(laptop.getId());
        dto.setUserName(laptop.getUser().getName());
        dto.setTitle(laptop.getTitle());
        dto.setMetaTitle(laptop.getMetaTitle());
        dto.setSlug(laptop.getSlug());
        dto.setSummary(laptop.getSummary());
        dto.setSku(laptop.getSku());
        dto.setPrice(laptop.getPrice());
        dto.setDiscount(laptop.getDiscount());
        dto.setQuantity(laptop.getQuantity());
        dto.setCategoryId(laptop.getCategory().getId());
        dto.setBrandId(laptop.getBrand().getId());
        return dto;
    }


}
