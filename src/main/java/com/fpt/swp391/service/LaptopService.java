package com.fpt.swp391.service;

import java.util.List;
import com.fpt.swp391.model.Laptop;

public interface LaptopService {


    List<Laptop> listAllLaptop();

    Laptop findById(Long id);

    Laptop createLaptop(Laptop laptop);

    boolean deleteLaptop(Long id);

    Laptop updateLaptop(Long id, Laptop laptop);
}
