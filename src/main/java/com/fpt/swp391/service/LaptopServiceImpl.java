package com.fpt.swp391.service;

import com.fpt.swp391.dto.DiscountDto;
import com.fpt.swp391.dto.LaptopDto;
import com.fpt.swp391.dto.MetadataDto;
import com.fpt.swp391.model.*;
import com.fpt.swp391.repository.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LaptopServiceImpl implements LaptopService {

    private final LaptopRepository laptopRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final UserRepository userRepository;
    private final DiscountRepository discountRepository;

    public LaptopServiceImpl(LaptopRepository laptopRepository, CategoryRepository categoryRepository, BrandRepository brandRepository, UserRepository userRepository, DiscountRepository discountRepository) {
        this.laptopRepository = laptopRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.userRepository = userRepository;
        this.discountRepository = discountRepository;
    }

    @Override
    public List<LaptopDto> listAllLaptop() {
        List<Laptop> lt = laptopRepository.findAll();
        List<LaptopDto> lt1 = new ArrayList<>();
        for (Laptop laptop : lt) {
            LaptopDto laptopDto = new LaptopDto();
            laptopDto.setId(laptop.getId());
            laptopDto.setUserName(laptop.getUser().getUsername());
            laptopDto.setTitle(laptop.getTitle());
            laptopDto.setMetaTitle(laptop.getMetaTitle());
            laptopDto.setSlug(laptop.getSlug());
            laptopDto.setSummary(laptop.getSummary());
            laptopDto.setImage(laptop.getImage());
            laptopDto.setSku(laptop.getSku());
            laptopDto.setPrice(laptop.getPrice());
            laptopDto.setQuantity(laptop.getQuantity());
            laptopDto.setStatus(laptop.isStatus());
            laptopDto.setDiscount(laptop.getDiscount());
            laptopDto.setCategoryId(laptop.getCategory().getId());
            laptopDto.setBrandId(laptop.getBrand().getId());
            lt1.add(laptopDto);
        }
        Collections.reverse(lt1);
        return lt1;
    }

    @Override
    public List<LaptopDto> listAllLaptopWithStatus() {
        try {
            List<LaptopDto> listDto = new ArrayList<>();
            List<Laptop> listLaptop = laptopRepository.findAll();
            for (Laptop laptop : listLaptop) {
                if (laptop.isStatus()) {
                    LaptopDto laptopDto = new LaptopDto();
                    laptopDto.setId(laptop.getId());
                    laptopDto.setUserName(laptop.getUser().getUsername());
                    laptopDto.setTitle(laptop.getTitle());
                    laptopDto.setMetaTitle(laptop.getMetaTitle());
                    laptopDto.setSlug(laptop.getSlug());
                    laptopDto.setSummary(laptop.getSummary());
                    laptopDto.setImage(laptop.getImage());
                    laptopDto.setSku(laptop.getSku());
                    laptopDto.setPrice(laptop.getPrice());
                    laptopDto.setQuantity(laptop.getQuantity());
                    laptopDto.setStatus(laptop.isStatus());
                    laptopDto.setDiscount(laptop.getDiscount());
                    laptopDto.setCategoryId(laptop.getCategory().getId());
                    laptopDto.setBrandId(laptop.getBrand().getId());
                    listDto.add(laptopDto);
                }
            }
            return listDto;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public LaptopDto findById(Long id) {
        Optional<Laptop> laptopOptional = laptopRepository.findById(id);
        if (laptopOptional.isPresent()) {
            Laptop laptop = laptopOptional.get();
            LaptopDto dto = convertToLaptopDto(laptop);
            return dto;
        }
        return null;
    }

    @Override
    public Laptop getById(Long id) {
        Optional<Laptop> laptopOptional = laptopRepository.findById(id);
        if (laptopOptional.isPresent()) {
            Laptop laptop = laptopOptional.get();
            return laptop;
        }
        return null;
    }

    @Override
    public Page<LaptopDto> getProductsByFilter(String listCategoryId, String listBrandId, String sortDirection, String priceOrder, float minPrice, float maxPrice, int pageSize, int pageNumber) {
        try {
            pageNumber -= 1;
            Specification<Laptop> specification = LaptopSpecifications.hasCategoryAndBrand(listCategoryId, listBrandId, priceOrder, sortDirection).and(LaptopSpecifications.hasPriceBetween(minPrice, maxPrice));
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Page<Laptop> resultPage = laptopRepository.findAll(specification, pageable);
            int totalPages = resultPage.getTotalPages();
            long totalElements = resultPage.getTotalElements();
            if (pageNumber >= totalPages && totalElements > 0) {
                pageNumber = totalPages - 1;
                pageable = PageRequest.of(pageNumber, pageSize);
                resultPage = laptopRepository.findAll(specification, pageable);
            }

            // Lọc các LaptopDto có status == true
            List<LaptopDto> filteredLaptopDtos = resultPage
                    .stream()
                    .map(this::convertToLaptopDto)
                    .filter(LaptopDto::isStatus) // Chỉ lấy các LaptopDto có status == true
                    .collect(Collectors.toList());

            // Tạo một trang mới với các LaptopDto đã lọc
            Page<LaptopDto> filteredResultPage = new PageImpl<>(filteredLaptopDtos, pageable, totalElements);

            return filteredResultPage;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Laptop createLaptop(LaptopDto laptop) {
        Laptop lt = new Laptop();
        lt.setTitle(laptop.getTitle());
        lt.setMetaTitle(laptop.getMetaTitle());
        lt.setSummary(laptop.getSummary());
        lt.setImage(laptop.getImage());
        lt.setSku(laptop.getSku());
        lt.setPrice(laptop.getPrice());
        lt.setDiscount(laptop.getDiscount());
        lt.setQuantity(laptop.getQuantity());
        lt.setStatus(laptop.isStatus());

        User u = userRepository.findByUsername(laptop.getUserName());
        lt.setUser(u);
        Category c = categoryRepository.findById(laptop.getCategoryId()).orElse(null);
        lt.setCategory(c);

        Brand b = brandRepository.findById(laptop.getBrandId()).orElse(null);
        lt.setBrand(b);

        laptopRepository.save(lt);
        lt.setSlug(lt.getSlug() + "-" + lt.getId());
        laptopRepository.save(lt);

        return lt;
    }

    @Override
    public Page<LaptopDto> getProducts(int page, int size, String sortBy, String sortOrder) {
        page = page - 1;
        try {
            if ((sortOrder.equals("asc")) || (sortOrder.equals("desc"))) {
                Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
                Pageable pageable = PageRequest.of(page, size, sort);
                Page<Laptop> productPage = laptopRepository.findAll(pageable);
                return productPage.map(this::convertToLaptopDto);
            } else {
                Pageable pageable = PageRequest.of(page, size);
                Page<Laptop> productPage = laptopRepository.findAll(pageable);
                return productPage.map(this::convertToLaptopDto);
            }

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public LaptopDto updateLaptop(Long id, LaptopDto laptop) {
        Optional<Laptop> laptopOptional = laptopRepository.findById(id);
        if (laptopOptional.isPresent()) {
            Laptop lt = laptopOptional.get();
            lt.setId(laptop.getId());
            lt.setTitle(laptop.getTitle());
            lt.setMetaTitle(laptop.getMetaTitle());
            if(!lt.getSlug().equals(laptop.getSlug())) {
                lt.setSlug(laptop.getSlug() + "-" + laptop.getId());
            }
            lt.setSummary(laptop.getSummary());
            lt.setImage(laptop.getImage());
            lt.setDiscount(laptop.getDiscount());
            lt.setPrice(laptop.getPrice());
            lt.setQuantity(laptop.getQuantity());
            lt.setStatus(laptop.isStatus());
            Category c = categoryRepository.findById(laptop.getCategoryId()).orElse(new Category());
            lt.setCategory(c);
            Brand b = brandRepository.findById(laptop.getBrandId()).orElse(new Brand());
            lt.setBrand(b);
            laptopRepository.save(lt);
            LaptopDto dto = convertToLaptopDto(lt);
            return dto;
        }
        return null;
    }

    @Override
    public LaptopDto getLaptopBySlug(String slug) {
        Laptop laptop = laptopRepository.findLaptopBySlug(slug);
        if (laptop != null) {
            return convertToLaptopDto(laptop);
        }
        return null;
    }

    public List<LaptopDto> getLaptopsByDiscountId(Long discountId) {
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new RuntimeException("Discount not found with ID: " + discountId));
        Set<Laptop> laptops = discount.getLaptops();
        List<LaptopDto> laptopDtos = new ArrayList<>();
        for (Laptop laptop : laptops) {
            LaptopDto laptopDto = convertToLaptopDto(laptop);
            laptopDtos.add(laptopDto);
        }
        return laptopDtos;
    }

    private LaptopDto convertToLaptopDto(Laptop laptop) {
        LaptopDto dto = new LaptopDto();
        dto.setId(laptop.getId());
        dto.setUserName(laptop.getUser().getName());
        dto.setTitle(laptop.getTitle());
        dto.setMetaTitle(laptop.getMetaTitle());
        dto.setSlug(laptop.getSlug());
        dto.setSummary(laptop.getSummary());
        dto.setImage(laptop.getImage());
        dto.setSku(laptop.getSku());
        dto.setDiscount(laptop.getDiscount());
        dto.setPrice(laptop.getPrice());
        Set<Discount> discountSet = laptop.getDiscounts();
        Set<DiscountDto> discountDtoSet = new HashSet<>();
        for (Discount discount : discountSet) {
            DiscountDto ddto = convertToDiscountDto(discount);
            discountDtoSet.add(ddto);
        }
        dto.setDiscountDtoSet(discountDtoSet);
        dto.setQuantity(laptop.getQuantity());
        dto.setStatus(laptop.isStatus());
        dto.setCategoryId(laptop.getCategory().getId());
        dto.setBrandId(laptop.getBrand().getId());
        Set<Metadata> metadataSet = laptop.getListMetadata();
        Set<MetadataDto> metadataDtoSet = new HashSet<>();
        for (Metadata meta : metadataSet) {
            MetadataDto mdto = converToMetaDataDto(meta);
            metadataDtoSet.add(mdto);
        }
        dto.setMetadataDtoSet(metadataDtoSet);
        return dto;
    }

    private MetadataDto converToMetaDataDto(Metadata metadata) {
        MetadataDto dto = new MetadataDto();
        dto.setId(metadata.getId());
        dto.setIcon(metadata.getIcon());
        dto.setTitle(metadata.getTitle());
        dto.setContent(metadata.getContent());
        dto.setLaptop_id(metadata.getLaptop().getId());
        dto.setGroup_id(metadata.getMetadataGroup().getId());
        return dto;
    }

    private DiscountDto convertToDiscountDto(Discount discount) {
        DiscountDto dto = new DiscountDto();
        dto.setId(discount.getId());
        dto.setDescription(discount.getDescription());
        dto.setCreateDate(discount.getCreateDate());
        dto.setQuantity(discount.getQuantity());
        dto.setStatus(discount.getStatus());
        return dto;
    }
}