package com.fpt.swp391.service;

import com.fpt.swp391.dto.FAPsDto;
import com.fpt.swp391.model.FAPs;

import java.util.List;

public interface FAPsService {

    FAPs findById(Long id);
    FAPsDto create(FAPsDto fapsdto);

    List<FAPsDto> listAllFaps();

    boolean deleteFapsById(Long id);

    boolean createFapsMultiple(List<FAPsDto> listFaps, Long laptopId);

    FAPs updateFaps(Long id, FAPsDto faPs);
}
