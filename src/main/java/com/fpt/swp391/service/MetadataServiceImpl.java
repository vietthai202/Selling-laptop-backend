package com.fpt.swp391.service;

import com.fpt.swp391.dto.LaptopDto;
import com.fpt.swp391.dto.MetadataDto;
import com.fpt.swp391.model.Laptop;
import com.fpt.swp391.model.Metadata;
import com.fpt.swp391.model.MetadataGroup;
import com.fpt.swp391.repository.LaptopRepository;
import com.fpt.swp391.repository.MetadataGroupRepository;
import com.fpt.swp391.repository.MetadataRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MetadataServiceImpl implements MetadataService {
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
        for (Metadata metadata : m) {
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
        if (metadataOptional.isPresent()) {
            Metadata metadata = metadataOptional.get();
            return metadata;
        }
        return null;
    }

    @Override
    public MetadataDto createMetadata(MetadataDto metadataDto) {
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
        MetadataDto dto = convertMetadataDto(m);
        return dto;
    }

    @Override
    public boolean createMetadataMultiple(List<MetadataDto> metadataDtoList, Long laptopId) {
        try {
            for (MetadataDto dto : metadataDtoList) {
                Metadata m = new Metadata();
                m.setTitle(dto.getTitle());
                m.setContent(dto.getContent());
                m.setIcon(dto.getIcon());
                m.setIconType(dto.getIconType());
                MetadataGroup g = metadataGroupRepository.findById(dto.getGroup_id()).orElse(null);
                if (g != null) {
                    m.setMetadataGroup(g);
                }
                Laptop l = laptopRepository.findById(laptopId).orElse(null);
                if (l != null) {
                    m.setLaptop(l);
                }
                metadataRepository.save(m);
            }
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public boolean deleteMetadata(Long id) {
        Optional<Metadata> metadataOptional = metadataRepository.findById(id);
        if (metadataOptional.isPresent()) {
            Metadata m = metadataOptional.get();
            metadataRepository.delete(m);
            return true;
        }
        return false;
    }

    @Override
    public Metadata updateMetadata(Long id, MetadataDto metadataDto) {
        Metadata m = metadataRepository.findById(id).orElse(null);
        if(m != null){
            Metadata meta = new Metadata();
            meta.setContent(metadataDto.getContent());
            meta.setTitle(metadataDto.getTitle());
            meta.setIcon(metadataDto.getIcon());
            Laptop lt = laptopRepository.findById(metadataDto.getLaptop_id()).orElse(new Laptop());
            meta.setLaptop(lt);
            MetadataGroup mg = metadataGroupRepository.findById(metadataDto.getGroup_id()).orElse(new MetadataGroup());
            meta.setMetadataGroup(mg);
            metadataRepository.save(meta);
            return meta;
        }
        return null;
    }

    @Override
    public boolean updateMetadataByLaptop(String slug, List<MetadataDto> metadataDtoList) {
        try {
            Laptop l = laptopRepository.findLaptopBySlug(slug);
            if (l != null) {
                List<Metadata> listMetadata = metadataRepository.findMetadataByLaptop_Id(l.getId());
                for (MetadataDto dto : metadataDtoList) {
                    for (Metadata meta : listMetadata) {
                        if (dto.getId() == meta.getId()) {
                            Metadata m = metadataRepository.findById(dto.getId()).orElse(null);
                            if (m != null) {
                                m.setContent(dto.getContent());
                                m.setIcon(dto.getIcon());
                                m.setTitle(dto.getTitle());
                                m.setIconType(dto.getIconType());
                                metadataRepository.save(m);
                            }
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    private MetadataDto convertMetadataDto (Metadata metadata){
        MetadataDto dto = new MetadataDto();
        dto.setId(metadata.getId());
        dto.setTitle(metadata.getTitle());
        dto.setContent(metadata.getContent());
        dto.setIcon(metadata.getIcon());
       dto.setLaptop_id(metadata.getLaptop().getId());
       dto.setGroup_id(metadata.getMetadataGroup().getId());
       return dto;
    }

}
