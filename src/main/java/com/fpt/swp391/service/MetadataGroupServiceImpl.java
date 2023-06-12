package com.fpt.swp391.service;

import com.fpt.swp391.dto.MetadataDto;
import com.fpt.swp391.dto.MetadataGroupDto;
import com.fpt.swp391.model.Laptop;
import com.fpt.swp391.model.Metadata;
import com.fpt.swp391.model.MetadataGroup;
import com.fpt.swp391.repository.LaptopRepository;
import com.fpt.swp391.repository.MetadataGroupRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MetadataGroupServiceImpl implements MetadataGroupService {
    private final MetadataGroupRepository metadataGroupRepository;
    private final LaptopRepository laptopRepository;

    public MetadataGroupServiceImpl(MetadataGroupRepository metadataGroupRepository, LaptopRepository laptopRepository) {
        this.metadataGroupRepository = metadataGroupRepository;
        this.laptopRepository = laptopRepository;
    }

    @Override
    public MetadataGroup createMetadataGroup(MetadataGroup metadataGroup) {
        MetadataGroup mg = new MetadataGroup();
        mg.setName(metadataGroup.getName());
        metadataGroupRepository.save(mg);
        return mg;
    }

    @Override
    public MetadataGroup updateMetaById(Long id, MetadataGroup metadataGroup) {
        Optional<MetadataGroup> m = metadataGroupRepository.findById(id);
        if(m.isPresent()){
            MetadataGroup m1 = new MetadataGroup();
            m1.setName(metadataGroup.getName());
            metadataGroupRepository.save(m1);
            return m1;
        }
        return null;
    }

    @Override
    public List<MetadataGroup> listAll() {
        return metadataGroupRepository.findAll();
    }

    @Override
    public List<MetadataGroupDto> findByLaptopSlug(String slug) {
        List<MetadataGroup> metadataGroupList = metadataGroupRepository.findMetadataGroupByLaptopSlug(slug);
        List<MetadataGroupDto> dto = new ArrayList<>();
        for (MetadataGroup metagroup: metadataGroupList) {
            dto.add(convertToMetadataGroupDto(metagroup, slug));
        }
        if(dto.size() > 0) {
            return dto;
        }
        return null;
    }

    private MetadataGroupDto convertToMetadataGroupDto(MetadataGroup metadataGroup, String slug) {
        MetadataGroupDto dto = new MetadataGroupDto();
        dto.setId(metadataGroup.getId());
        dto.setName(metadataGroup.getName());
        Set<Metadata> metadataSet = metadataGroup.getMetadatas();
        Set<MetadataDto> metadataDtoSet = new HashSet<>();

        Laptop l = laptopRepository.findLaptopBySlug(slug);

        for (Metadata m: metadataSet) {
            if(l.getId() == m.getLaptop().getId())
                metadataDtoSet.add(convertToMetaDataDto(m));
        }
        dto.setMetadataDtoSet(metadataDtoSet);
        return dto;
    }

    private MetadataDto convertToMetaDataDto(Metadata metadata){
        MetadataDto dto = new MetadataDto();
        dto.setId(metadata.getId());
        dto.setIcon(metadata.getIcon());
        dto.setLaptop_id(metadata.getLaptop().getId());
        dto.setTitle(metadata.getTitle());
        dto.setContent(metadata.getContent());
        dto.setGroup_id(metadata.getMetadataGroup().getId());
        return dto;
    }
}
