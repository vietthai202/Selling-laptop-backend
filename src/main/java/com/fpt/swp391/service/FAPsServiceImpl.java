package com.fpt.swp391.service;

import com.fpt.swp391.dto.FAPsDto;
import com.fpt.swp391.model.FAPs;
import com.fpt.swp391.model.Laptop;
import com.fpt.swp391.repository.FAPsRepository;
import com.fpt.swp391.repository.LaptopRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FAPsServiceImpl implements FAPsService{
    private final FAPsRepository faPsRepository;

    private final LaptopRepository laptopRepository;

    public FAPsServiceImpl(FAPsRepository faPsRepository, LaptopRepository laptopRepository) {
        this.faPsRepository = faPsRepository;
        this.laptopRepository = laptopRepository;
    }

    @Override
    public FAPs findById(Long id) {
        return faPsRepository.findById(id).orElse(null);
    }

    @Override
    public FAPsDto create(FAPsDto fapsdto) {
        FAPs f = new FAPs();
        Laptop l = laptopRepository.findById(fapsdto.getLaptop_id()).orElse(null);
        if(l != null){
            f.setTitle(fapsdto.getTitle());
            f.setContent(fapsdto.getContent());
            f.setLaptop(l);
            faPsRepository.save(f);
            FAPsDto dto = convertFAPsDto(f);
            return dto;
        }
        return null;
    }

    @Override
    public List<FAPsDto> listAllFaps() {
        try{
            List<FAPs> f1 = faPsRepository.findAll();
            List<FAPsDto> f2 = new ArrayList<>();
            for(FAPs f : f1){
                FAPsDto dto = new FAPsDto();
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
    public boolean deleteFapsById(Long id) {
        try{
            FAPs f = faPsRepository.findById(id).orElse(null);
            if(f != null){
                faPsRepository.delete(f);
                return true;
            }
        }catch (Exception e){

        }
        return false;
    }

    @Override
    public boolean createFapsMultiple(List<FAPsDto> listFaps, Long laptopId) {
        try{
            for (FAPsDto dto : listFaps){
                FAPs f = new FAPs();
                f.setTitle(dto.getTitle());
                f.setContent(dto.getContent());
                Laptop lt = laptopRepository.findById(laptopId).orElse(null);
                if(lt != null) {
                    f.setLaptop(lt);
                }
                faPsRepository.save(f);
            }
            return true;
        }catch(Exception e){

        }
        return false;
    }

    @Override
    public FAPs updateFaps(Long id, FAPsDto faPs) {
        FAPs faps = faPsRepository.findById(id).orElse(null);
        if(faps != null){
            faps.setContent(faPs.getContent());
            faps.setTitle(faPs.getTitle());
            Laptop lt =laptopRepository.findById(faPs.getLaptop_id()).orElse(new Laptop());
            faps.setLaptop(lt);
            faPsRepository.save(faps);
            return faps;
        }
        return null;
    }

    private FAPsDto convertFAPsDto(FAPs faps){
        FAPsDto ft = new FAPsDto();
        ft.setId(faps.getId());
        ft.setTitle(faps.getTitle());
        ft.setContent(faps.getContent());
        ft.setLaptop_id(faps.getLaptop().getId());
        return ft;
    }
}
