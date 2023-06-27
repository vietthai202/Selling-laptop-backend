package com.fpt.swp391.service;

import com.fpt.swp391.dto.ReceiveAddressDto;
import com.fpt.swp391.dto.ReceiveAddressRequestDto;
import com.fpt.swp391.model.ReceiveAddress;

import java.util.List;

public interface ReceiveAddressService {
    ReceiveAddress findById(Long id);

    ReceiveAddressDto findReceiverById(Long id);

    ReceiveAddressDto createReceiveAddress (ReceiveAddressDto receiveAddress);

    ReceiveAddressDto updateReceiveAddress(ReceiveAddressRequestDto receiveAddressRequestDto);

    boolean deleteReceiveAddress(Long id);

    List<ReceiveAddressDto> listReceiveAddressByUserId(Long userId);

    ReceiveAddressDto getReceiveById(Long id);

    ReceiveAddressDto updateByDefaultAddress(Long id, ReceiveAddressDto receiveAddressDto);
}
