package com.fpt.swp391.service;

import java.util.List;
import java.util.Optional;

import com.fpt.swp391.model.Laptop;
import com.fpt.swp391.repository.LaptopRepository;

public class LaptopServiceImpl implements LaptopService{
    private LaptopRepository laptopRepository = null;

    @Override
    public List<Laptop> listAllLaptop(){
        return laptopRepository.findAll();
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
    public Laptop createLaptop(Laptop laptop) {
        Laptop lt = new Laptop();
        lt.setUser(laptop.getUser());
        lt.setTitle(laptop.getTitle());
        lt.setMetaTitle(laptop.getMetaTitle());
        lt.setSlug(laptop.getSlug());
        lt.setSummary(laptop.getSummary());
        lt.setSku(laptop.getSku());
        lt.setPrice(laptop.getPrice());
        lt.setDiscount(laptop.getDiscount());
        lt.setQuantity(laptop.getQuantity());
        lt.setMetadata(laptop.getMetadata());
        lt.setTags(laptop.getTags());
        lt.setCategory(laptop.getCategory());
        lt.setBrand(laptop.getBrand());
        lt.setCartItems(laptop.getCartItems());
        lt.setListFaps(laptop.getListFaps() );
        lt.setOrderItems(laptop.getOrderItems());
        lt.setReviews(laptop.getReviews());
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
    public Laptop updateLaptop(Long id, Laptop laptop){
        Optional<Laptop> laptopOptional = laptopRepository.findById(id);
        if(laptopOptional.isPresent()){
            Laptop lt = laptopOptional.get();
            lt.setUser(laptop.getUser());
            lt.setTitle(laptop.getTitle());
            lt.setMetaTitle(laptop.getMetaTitle());
            lt.setSlug(laptop.getSlug());
            lt.setSummary(laptop.getSummary());
            lt.setSku(laptop.getSku());
            lt.setPrice(laptop.getPrice());
            lt.setDiscount(laptop.getDiscount());
            lt.setQuantity(laptop.getQuantity());
            lt.setMetadata(laptop.getMetadata());
            lt.setTags(laptop.getTags());
            lt.setCategory(laptop.getCategory());
            lt.setBrand(laptop.getBrand());
            lt.setCartItems(laptop.getCartItems());
            lt.setListFaps(laptop.getListFaps() );
            lt.setOrderItems(laptop.getOrderItems());
            lt.setReviews(laptop.getReviews());
            laptopRepository.save(lt);
            return lt;
        }
        return null;
    }
}
