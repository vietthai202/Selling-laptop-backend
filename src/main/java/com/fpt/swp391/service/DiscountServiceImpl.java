package com.fpt.swp391.service;

import com.fpt.swp391.dto.DiscountDto;
import com.fpt.swp391.model.Discount;
import com.fpt.swp391.model.Laptop;
import com.fpt.swp391.repository.DiscountRepository;
import com.fpt.swp391.repository.LaptopRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class DiscountServiceImpl implements DiscountService{
    private final DiscountRepository discountRepository;
    private final LaptopRepository laptopRepository;

    public DiscountServiceImpl(DiscountRepository discountRepository, LaptopRepository laptopRepository) {
        this.discountRepository = discountRepository;
        this.laptopRepository = laptopRepository;
    }

    @Override
    public Discount createDiscount(DiscountDto discountDto) {
        Discount discount = new Discount();
        discount.setName(generateRandomString());
        discount.setDescription(discountDto.getDescription());
        discount.setQuantity(discountDto.getQuantity());
        discount.setCreateDate(new Date());
        discount.setStatus(Boolean.TRUE);
        discountRepository.save(discount);
        return discount;
    }

    @Override
    public Discount getDiscountById(Long id){
        Optional<Discount> discountOptional = discountRepository.findById(id);
        return discountOptional.orElse(null);
    }

    @Override
    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }
    @Override
    public List<DiscountDto> getAllDiscountDtos() {
        List<Discount> discounts = getAllDiscounts();
        List<DiscountDto> discountDtos = new ArrayList<>();
        for (Discount discount : discounts) {
            DiscountDto discountDto = convertToDto(discount);
            discountDtos.add(discountDto);
        }
        return discountDtos;
    }

    @Override
    public Discount updateDiscount(Discount discount){
        Optional<Discount> discountOptional = discountRepository.findById(discount.getId());
        if(discountOptional.isPresent()){
            Discount oldDiscount = discountOptional.get();
            Discount newDiscount = new Discount();
            newDiscount.setName(oldDiscount.getName());
            newDiscount.setDescription(oldDiscount.getDescription());

            return newDiscount;
        }
        return null;
    }

    public Discount addLaptopsToDiscount(String discountId, List<String> laptopIds) {
        Long discountIdLong = Long.parseLong(discountId);
        Discount discount = discountRepository.findById(discountIdLong).orElse(null);
        if (discount != null) {
            List<Long> laptopIdsLong = laptopIds.stream()
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
            List<Laptop> laptops = laptopRepository.findAllById(laptopIdsLong);
            discount.getLaptops().addAll(laptops);
            discountRepository.save(discount);
            return discount;
        }
        return null;
    }

    public List<DiscountDto> getDiscountsByLaptopId(Long laptopId) {
        List<Discount> discounts = laptopRepository.findDiscountsByLaptopId(laptopId);
        return discounts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DiscountDto convertToDto(Discount discount) {
        DiscountDto discountDto = new DiscountDto();
        discountDto.setId(discount.getId());
        discountDto.setDescription(discount.getDescription());
        discountDto.setQuantity(discount.getQuantity());
        discountDto.setCreateDate(discount.getCreateDate());
        discountDto.setStatus(discount.getStatus());
        return discountDto;
    }

    @Override
    public void updateDiscountStatus(Long discountId, Boolean newStatus) {
        Optional<Discount> optionalDiscount = discountRepository.findById(discountId);
        if (optionalDiscount.isPresent()) {
            Discount discount = optionalDiscount.get();
            discount.setStatus(newStatus);
            discountRepository.save(discount);
        } else {
            throw new RuntimeException("Not found");
        }
    }

    @Override
    public void updateDiscount(Long discountId, DiscountDto discountDto) {
        Optional<Discount> optionalDiscount = discountRepository.findById(discountId);
        if (optionalDiscount.isPresent()) {
            Discount discount = optionalDiscount.get();
            discount.setDescription(discountDto.getDescription());
            discount.setQuantity(discountDto.getQuantity());
            discount.setCreateDate(discountDto.getCreateDate());
            discount.setStatus(discountDto.getStatus());
            discountRepository.save(discount);
        } else {
            throw new RuntimeException("Discount not found with ID: " + discountId);
        }
    }

    private String generateRandomString() {
        int length = 5;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomString.append(randomChar);
        }
        return randomString.toString();
    }

    @Override
    public void deleteDiscount(Long discountId) {
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new RuntimeException("Discount not found with ID: " + discountId));
        Set<Laptop> laptops = discount.getLaptops();
        for (Laptop laptop : laptops) {
            laptop.getDiscounts().remove(discount);
        }
        discountRepository.delete(discount);
    }

    @Override
    public void removeLaptopFromDiscount(Long discountId, Long laptopId) {
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new RuntimeException("Discount not found with ID: " + discountId));

        Laptop laptop = laptopRepository.findById(laptopId)
                .orElseThrow(() -> new RuntimeException("Laptop not found with ID: " + laptopId));

        discount.getLaptops().remove(laptop);
        discountRepository.save(discount);
    }
}