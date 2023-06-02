package com.fpt.swp391.service;

import com.fpt.swp391.dto.LaptopImgDto;

import java.util.List;

public interface LaptopImgService {
    LaptopImgDto create(LaptopImgDto laptopImg);

    List<LaptopImgDto> listAllImg();

    boolean deleteImgById(Long id);
}
