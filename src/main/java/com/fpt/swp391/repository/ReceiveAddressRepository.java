package com.fpt.swp391.repository;

import com.fpt.swp391.model.ReceiveAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceiveAddressRepository extends JpaRepository<ReceiveAddress, Long> {
    List<ReceiveAddress> findReceiveAddressByUserId(Long userId);

    List<ReceiveAddress> findReceiveAddressById(Long id);
}
