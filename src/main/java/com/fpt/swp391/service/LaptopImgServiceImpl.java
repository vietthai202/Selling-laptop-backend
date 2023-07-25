package com.fpt.swp391.service;

import com.fpt.swp391.dto.LaptopImgDto;
import com.fpt.swp391.model.Laptop;
import com.fpt.swp391.model.LaptopImg;
import com.fpt.swp391.repository.LaptopImgRepository;
import com.fpt.swp391.repository.LaptopRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LaptopImgServiceImpl implements LaptopImgService {
    private final LaptopImgRepository laptopImgRepository;

    private final LaptopRepository laptopRepository;

    public LaptopImgServiceImpl(LaptopImgRepository laptopImgRepository, LaptopRepository laptopRepository) {
        this.laptopImgRepository = laptopImgRepository;
        this.laptopRepository = laptopRepository;
    }

    @Override
    public LaptopImgDto create(LaptopImgDto laptopImg) {
        LaptopImg img = new LaptopImg();
        Laptop l = laptopRepository.findById(laptopImg.getLaptop_id()).orElse(null);
        if(l != null) {
            img.setImage(laptopImg.getImage());
            img.setLaptop(l);
            laptopImgRepository.save(img);
            LaptopImgDto dto = convertToLaptopImgDto(img);
            return dto;
        }
        return null;
    }

    @Override
    public void createMultiple(LaptopImgDto laptopImg) {
        Laptop l = laptopRepository.findById(laptopImg.getLaptop_id()).orElse(null);
        if(l != null) {
            String[] imgArray = laptopImg.getImage().split("##swp##");
            for (int i = 0; i < imgArray.length; i++) {
                LaptopImg img = new LaptopImg();
                img.setImage(imgArray[i]);
                img.setLaptop(l);
                laptopImgRepository.save(img);
            }
        }
    }

    @Override
    public List<LaptopImgDto> listAllImg(Long id) {
        try{
            List<LaptopImg> img = laptopImgRepository.findAllByLaptop_Id(id);
            List<LaptopImgDto> img1 = new ArrayList<>();
            for(LaptopImg l : img){
                LaptopImgDto dto = new LaptopImgDto();
                dto.setId(l.getId());
                dto.setImage(l.getImage());
                img1.add(dto);
            }
            return img1;
        }catch(Exception e){
            return null;
        }

    }

    @Override
    public boolean deleteImgById(Long id) {
        try{
            LaptopImg img = laptopImgRepository.findById(id).orElse(null);
            if(img != null){
                laptopImgRepository.delete(img);
                return  true;
            }
        }catch(Exception e){

        }
        return false;
    }

    @Override
    public LaptopImg findById(Long id) {
        LaptopImg img = laptopImgRepository.findById(id).orElse(null);
        if(img != null){
            return img;
        }
        return null;
    }

    private LaptopImgDto convertToLaptopImgDto(LaptopImg laptopImg){
        LaptopImgDto lt = new LaptopImgDto();
        lt.setId(laptopImg.getId());
        lt.setImage(laptopImg.getImage());
        lt.setLaptop_id(laptopImg.getLaptop().getId());
        return lt;
    }
}
