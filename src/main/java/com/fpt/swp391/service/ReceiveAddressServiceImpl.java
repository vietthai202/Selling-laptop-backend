package com.fpt.swp391.service;

import com.fpt.swp391.dto.ReceiveAddressDto;
import com.fpt.swp391.dto.ReceiveAddressRequestDto;
import com.fpt.swp391.model.ReceiveAddress;
import com.fpt.swp391.model.User;
import com.fpt.swp391.repository.ReceiveAddressRepository;
import com.fpt.swp391.repository.UserRepository;
import com.fpt.swp391.security.jwt.JwtTokenManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReceiveAddressServiceImpl implements ReceiveAddressService{
    private final ReceiveAddressRepository receiveAddressRepository;

    private final UserRepository userRepository;

    private final JwtTokenManager jwtTokenManager;

    public ReceiveAddressServiceImpl(ReceiveAddressRepository receiveAddressRepository, UserRepository userRepository, JwtTokenManager jwtTokenManager) {
        this.receiveAddressRepository = receiveAddressRepository;
        this.userRepository = userRepository;
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    public ReceiveAddress findById(Long id) {
        try{
            ReceiveAddress rc = receiveAddressRepository.findById(id).orElse(null);
            if(rc != null){
                ReceiveAddressDto ra = convertReceiveAddressDto(rc);
                return rc;
            }
        } catch (Exception e){

        }
        return null;
    }

    @Override
    public ReceiveAddressDto findReceiverById(Long id) {
        try{
            ReceiveAddress rc = receiveAddressRepository.findById(id).orElse(null);
            if(rc != null){
                ReceiveAddressDto ra = convertReceiveAddressDto(rc);
                return ra;
            }
        } catch (Exception e){

        }
        return null;
    }

    @Override
    public ReceiveAddressDto createReceiveAddress(ReceiveAddressDto receiveAddress) {
        try {
            ReceiveAddress rc = new ReceiveAddress();
            rc.setName(receiveAddress.getName());
            rc.setPhone(receiveAddress.getPhone());
            rc.setAddress(receiveAddress.getAddress());
            User user = userRepository.findById(receiveAddress.getUser_id()).orElse(new User());
            rc.setUser(user);
            rc.setDefaultaddress(receiveAddress.isDefaultaddress());
            receiveAddressRepository.save(rc);

            updateByDefaultAddress(rc.getId(), convertReceiveAddressDto(rc));
            return convertReceiveAddressDto(rc);
        } catch (Exception e){

        }
        return null;
    }

    @Override
    public ReceiveAddressDto updateReceiveAddress(ReceiveAddressRequestDto receiveAddress) {
        try{
            String name = jwtTokenManager.getUsernameFromToken(receiveAddress.getToken());
            User u = userRepository.findByUsername(receiveAddress.getUserName());
            ReceiveAddress rc = receiveAddressRepository.findById(receiveAddress.getId()).orElse(null);
            if(name.equalsIgnoreCase(u.getUsername())){
                if(rc != null) {
                    rc.setName(receiveAddress.getName());
                    rc.setPhone(receiveAddress.getPhone());
                    rc.setAddress(receiveAddress.getAddress());
                    rc.setDefaultaddress(receiveAddress.isDefaultaddress());
                    receiveAddressRepository.save(rc);
                }
            }
            return convertReceiveAddressDto(rc);
        } catch (Exception e){

        }
        return null;
    }

    @Override
    public boolean deleteReceiveAddress(Long id) {
        try{
            ReceiveAddress rc = receiveAddressRepository.findById(id).orElse(null);
            if(rc != null){
                receiveAddressRepository.delete(rc);
                return true;
            }
        } catch (Exception e){

        }
        return false;
    }

    @Override
    public List<ReceiveAddressDto> listReceiveAddressByUserId(Long userId) {
        try{
            User user = userRepository.findById(userId).orElse(null);
            List<ReceiveAddressDto> listDto = new ArrayList<>();
            if(user != null){
                List<ReceiveAddress> list = receiveAddressRepository.findReceiveAddressByUserId(user.getId());
                for(ReceiveAddress rc : list){
                    if(rc.getId() != null){
                        ReceiveAddressDto dto = new ReceiveAddressDto();
                        dto.setId(rc.getId());
                        dto.setName(rc.getName());
                        dto.setPhone(rc.getPhone());
                        dto.setAddress(rc.getAddress());
                        dto.setUser_id(rc.getUser().getId());
                        dto.setDefaultaddress(rc.isDefaultaddress());
                        listDto.add(dto);
                    }
                }
            }
            return listDto;
        } catch (Exception e){

        }
        return null;
    }

    @Override
    public ReceiveAddressDto getReceiveById(Long id) {
        ReceiveAddress ra = receiveAddressRepository.findById(id).orElse(null);
        if(ra != null) {
            return convertReceiveAddressDto(ra);
        }
        return null;
    }

    @Override
    public ReceiveAddressDto updateByDefaultAddress(Long id,ReceiveAddressDto receiveAddressDto) {
        try {
            List<ReceiveAddress> list = receiveAddressRepository.findAll();
            for (ReceiveAddress rc : list) {
                rc.setDefaultaddress(false);
                receiveAddressRepository.save(rc);
            }
            ReceiveAddress selected = receiveAddressRepository.findById(id).orElse(null);
            if (selected != null) {
                selected.setDefaultaddress(receiveAddressDto.isDefaultaddress());
                receiveAddressRepository.save(selected);
            }
            return receiveAddressDto;
        } catch (Exception e){
            return null;
        }
    }

    private ReceiveAddressDto convertReceiveAddressDto(ReceiveAddress receiveAddress){
        ReceiveAddressDto dto = new ReceiveAddressDto();
        dto.setId(receiveAddress.getId());
        dto.setName(receiveAddress.getName());
        dto.setPhone(receiveAddress.getPhone());
        dto.setAddress(receiveAddress.getAddress());
        dto.setUser_id(receiveAddress.getUser().getId());
        dto.setDefaultaddress(receiveAddress.isDefaultaddress());
        return dto;
    }
}
