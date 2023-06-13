package com.fpt.swp391.service;

import com.fpt.swp391.dto.FAQsDto;
import com.fpt.swp391.model.FAQs;

import java.util.List;

public interface FAQsService {

    FAQs findById(Long id);
    FAQsDto create(FAQsDto fapsdto);

    List<FAQsDto> listAllFaps();

    List<FAQsDto> listAllFaqsByLaptopId(Long id);

    boolean deleteFapsById(Long id);

    boolean createFapsMultiple(List<FAQsDto> listFaps, Long laptopId);

    FAQs updateFaps(Long id, FAQsDto faPs);
}
