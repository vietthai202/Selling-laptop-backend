package com.fpt.swp391.service;

import com.fpt.swp391.dto.FAQsDto;
import com.fpt.swp391.model.FAQs;
import com.fpt.swp391.model.Laptop;
import com.fpt.swp391.repository.FAQsRepository;
import com.fpt.swp391.repository.LaptopRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FAQsServiceImpl implements FAQsService {
    private final FAQsRepository faQsRepository;

    private final LaptopRepository laptopRepository;

    public FAQsServiceImpl(FAQsRepository faQsRepository, LaptopRepository laptopRepository) {
        this.faQsRepository = faQsRepository;
        this.laptopRepository = laptopRepository;
    }

    @Override
    public FAQs findById(Long id) {
        return faQsRepository.findById(id).orElse(null);
    }

    @Override
    public FAQsDto create(FAQsDto fapsdto) {
        FAQs f = new FAQs();
        Laptop l = laptopRepository.findById(fapsdto.getLaptop_id()).orElse(null);
        if(l != null){
            f.setTitle(fapsdto.getTitle());
            f.setContent(fapsdto.getContent());
            f.setLaptop(l);
            faQsRepository.save(f);
            FAQsDto dto = convertFAPsDto(f);
            return dto;
        }
        return null;
    }

    @Override
    public List<FAQsDto> listAllFaps() {
        try{
            List<FAQs> f1 = faQsRepository.findAll();
            List<FAQsDto> f2 = new ArrayList<>();
            for(FAQs f : f1){
                FAQsDto dto = new FAQsDto();
                dto.setId(f.getId());
                dto.setTitle(f.getTitle());
                dto.setContent(f.getContent());
                dto.setLaptop_id(f.getLaptop().getId());
                f2.add(dto);
            }
            return f2;
        }catch(Exception e){
            return null;
        }

    }

    @Override
    public List<FAQsDto> listAllFaqsByLaptopId(Long id) {
        try {
            Laptop laptop = laptopRepository.findById(id).orElse(null);
            List<FAQsDto> faqsDto = new ArrayList<>();
            if (laptop != null) {
                List<FAQs> faq = faQsRepository.findFAQsByLaptopId(laptop.getId());
                for (FAQs faqs : faq) {
                    if (faqs.getId() != null) {
                        FAQsDto f = new FAQsDto();
                        f.setId(faqs.getId());
                        f.setTitle(faqs.getTitle());
                        f.setContent(faqs.getContent());
                        f.setLaptop_id(faqs.getLaptop().getId());
                        faqsDto.add(f);
                    }
                }
            }
            return faqsDto;
        } catch (Exception e){

        }
        return null;
    }

    @Override
    public boolean deleteFapsById(Long id) {
        try{
            FAQs f = faQsRepository.findById(id).orElse(null);
            if(f != null){
                faQsRepository.delete(f);
                return true;
            }
        }catch (Exception e){

        }
        return false;
    }

    @Override
    public boolean createFapsMultiple(List<FAQsDto> listFaps, Long laptopId) {
        try{
            for (FAQsDto dto : listFaps){
                FAQs f = new FAQs();
                f.setTitle(dto.getTitle());
                f.setContent(dto.getContent());
                Laptop lt = laptopRepository.findById(laptopId).orElse(null);
                if(lt != null) {
                    f.setLaptop(lt);
                }
                faQsRepository.save(f);
            }
            return true;
        }catch(Exception e){

        }
        return false;
    }

    @Override
    public FAQs updateFaq(Long id, FAQsDto faPs) {
        FAQs faps = faQsRepository.findById(id).orElse(null);
        if(faps != null){
            faps.setContent(faPs.getContent());
            faps.setTitle(faPs.getTitle());
            faQsRepository.save(faps);
            return faps;
        }
        return null;
    }

    private FAQsDto convertFAPsDto(FAQs faps){
        FAQsDto ft = new FAQsDto();
        ft.setId(faps.getId());
        ft.setTitle(faps.getTitle());
        ft.setContent(faps.getContent());
        ft.setLaptop_id(faps.getLaptop().getId());
        return ft;
    }
}
