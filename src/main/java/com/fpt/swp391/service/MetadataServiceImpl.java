package com.fpt.swp391.service;

import com.fpt.swp391.dto.MetadataDto;
import com.fpt.swp391.model.Laptop;
import com.fpt.swp391.model.Metadata;
import com.fpt.swp391.model.MetadataGroup;
import com.fpt.swp391.repository.LaptopRepository;
import com.fpt.swp391.repository.MetadataGroupRepository;
import com.fpt.swp391.repository.MetadataRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MetadataServiceImpl implements MetadataService{
    private final MetadataRepository metadataRepository;

    private final MetadataGroupRepository metadataGroupRepository;

    private final LaptopRepository laptopRepository;

    public MetadataServiceImpl(MetadataRepository metadataRepository, MetadataGroupRepository metadataGroupRepository, LaptopRepository laptopRepository) {
        this.metadataRepository = metadataRepository;
        this.metadataGroupRepository = metadataGroupRepository;
        this.laptopRepository = laptopRepository;
    }

    @Override
    public List<MetadataDto> listAll() {
        List<Metadata> m = metadataRepository.findAll();
        List<MetadataDto> m1 = new ArrayList<>();
        for (Metadata metadata : m){
            MetadataDto metadataDto = new MetadataDto();
            metadataDto.setId(metadata.getId());
            metadataDto.setIcon(metadata.getIcon());
            metadataDto.setContent(metadata.getContent());
            metadataDto.setTitle(metadata.getTitle());
            metadataDto.setIconType(metadata.getIconType());
            metadataDto.setLaptop_id(metadata.getLaptop().getId());
            metadataDto.setGroup_id(metadata.getMetadataGroup().getId());
            m1.add(metadataDto);
        }
        return m1;
    }

    @Override
    public Metadata findById(Long id) {
        Optional<Metadata> metadataOptional = metadataRepository.findById(id);
        if(metadataOptional.isPresent()){
            Metadata metadata = metadataOptional.get();
            return metadata;
        }
        return null;
    }

    @Override
    public Metadata createMetadata(MetadataDto metadataDto) {
        Metadata m = new Metadata();
        m.setIcon(metadataDto.getIcon());
        m.setIconType(metadataDto.getIconType());
        m.setTitle(metadataDto.getTitle());
        m.setContent(metadataDto.getContent());
        MetadataGroup m1 = metadataGroupRepository.findById(metadataDto.getGroup_id()).orElse(new MetadataGroup());
        m.setMetadataGroup(m1);
        Laptop lt = laptopRepository.findById(metadataDto.getLaptop_id()).orElse(new Laptop());
        m.setLaptop(lt);
        metadataRepository.save(m);
        return m;
    }

    @Override
    public boolean deleteMetadata(Long id) {
        Optional<Metadata> metadataOptional = metadataRepository.findById(id);
        if(metadataOptional.isPresent()){
            Metadata m = metadataOptional.get();
            metadataRepository.delete(m);
            return true;
        }
        return false;
    }

    @Override
    public Metadata updateMetadata(Long id, MetadataDto metadataDto) {
        Optional<Metadata> metadataOptional = metadataRepository.findById(id);
        if(metadataOptional.isPresent()) {
            Metadata m = metadataOptional.get();
            m.setIcon(metadataDto.getIcon());
            m.setIconType(metadataDto.getIconType());
            m.setTitle(metadataDto.getTitle());
            m.setContent(metadataDto.getContent());
            MetadataGroup m1 = metadataGroupRepository.findById(metadataDto.getGroup_id()).orElse(new MetadataGroup());
            m.setMetadataGroup(m1);
            metadataRepository.save(m);
            return m;
        }
        return null;
    }
}
