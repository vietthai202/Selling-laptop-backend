package com.fpt.swp391.service;

import com.fpt.swp391.dto.BrandDto;
import com.fpt.swp391.model.Brand;
import com.fpt.swp391.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
            dto.setImage(dto.getImage());
            dto.setSlug(dto.getSlug());
            return dto;
        }
        return null;
    }


}
