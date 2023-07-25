package com.fpt.swp391.service;

import com.fpt.swp391.dto.LaptopImgDto;
import com.fpt.swp391.model.LaptopImg;

import java.util.List;

public interface LaptopImgService {
    LaptopImgDto create(LaptopImgDto laptopImg);

    void createMultiple(LaptopImgDto laptopImg);

    List<LaptopImgDto> listAllImg(Long id);

    boolean deleteImgById(Long id);

    LaptopImg findById(Long id);
}
