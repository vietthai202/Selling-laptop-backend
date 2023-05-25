package com.fpt.swp391.service;

import java.util.List;

import com.fpt.swp391.dto.LaptopDto;
import com.fpt.swp391.model.Laptop;

public interface LaptopService {

    List<LaptopDto> listAllLaptop();
    Laptop findById(Long id);

    Laptop createLaptop(LaptopDto laptopDto);

    boolean deleteLaptop(Long id);

    Laptop updateLaptop(Long id, LaptopDto laptop);

    LaptopDto getLaptopBySlug(String slug);
}
