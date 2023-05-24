package com.fpt.swp391.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fpt.swp391.dto.LaptopDto;
import com.fpt.swp391.model.Brand;
import com.fpt.swp391.model.Category;
import com.fpt.swp391.model.Laptop;
import com.fpt.swp391.model.User;
import com.fpt.swp391.repository.*;
import org.springframework.stereotype.Service;

@Service
public class LaptopServiceImpl implements LaptopService{

    private final LaptopRepository laptopRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final UserRepository userRepository;
    public LaptopServiceImpl( LaptopRepository laptopRepository, CategoryRepository categoryRepository, BrandRepository brandRepository, UserRepository userRepository) {
        this.laptopRepository = laptopRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<LaptopDto> listAllLaptop(){
        List<Laptop> lt = laptopRepository.findAll();
        List<LaptopDto> lt1 = new ArrayList<>();

        for (Laptop laptop:lt) {
            LaptopDto laptopDto = new LaptopDto();
            laptopDto.setUserName(laptop.getUser().getUsername());
            laptopDto.setTitle(laptop.getTitle());
            laptopDto.setMetaTitle(laptop.getMetaTitle());
            laptopDto.setSlug(laptop.getSlug());
            laptopDto.setSummary(laptop.getSummary());
            laptopDto.setSku(laptop.getSku());
            laptopDto.setPrice(laptop.getPrice());
            laptopDto.setDiscount(laptop.getDiscount());
            laptopDto.setQuantity(laptop.getQuantity());
            laptopDto.setCategoryId(laptop.getCategory().getId());
            laptopDto.setBrandId(laptop.getBrand().getId());
            lt1.add(laptopDto);

        }
        return lt1;
    }

    @Override
    public Laptop findById(Long id){
        Optional<Laptop> laptopOptional = laptopRepository.findById(id);
        if(laptopOptional.isPresent()){
            Laptop laptop = laptopOptional.get();
            return laptop;
        }
        return null;
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
        lt.setSku(laptop.getSku());
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
    public boolean deleteLaptop(Long id){
        Optional<Laptop> laptopOptional = laptopRepository.findById(id);
        if(laptopOptional.isPresent()){
            Laptop lt = laptopOptional.get();
            laptopRepository.delete(lt);
            return true;
        }
        return false;
    }

    @Override
    public Laptop updateLaptop(Long id, LaptopDto laptop){
        Optional<Laptop> laptopOptional = laptopRepository.findById(id);
        if(laptopOptional.isPresent()){
            Laptop lt = laptopOptional.get();
            lt.setTitle(laptop.getTitle());
            lt.setMetaTitle(laptop.getMetaTitle());
            lt.setSlug(laptop.getSlug());
            lt.setSummary(laptop.getSummary());
            lt.setSku(laptop.getSku());
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
}
