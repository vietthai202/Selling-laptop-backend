package com.fpt.swp391.service;
import com.fpt.swp391.dto.BrandDto;
import com.fpt.swp391.model.Brand;
import com.fpt.swp391.repository.BrandRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service

public class BrandServiceImpl implements BrandService{
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
        br.setSlug(brand.getSlug());
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
    public Brand updateBrand(Long id, BrandDto brandDto){
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if(brandOptional.isPresent()){
            Brand brand = brandOptional.get();
            brand.setName(brandDto.getName());
            brand.setImage(brandDto.getImage());
            brand.setSlug(brandDto.getSlug());
            brandRepository.save(brand);
            return brand;
        }
        return null;
    }


}
