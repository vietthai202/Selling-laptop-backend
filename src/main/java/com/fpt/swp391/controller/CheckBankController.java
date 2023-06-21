package com.fpt.swp391.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.swp391.model.Order;
import com.fpt.swp391.model.StatusEnum;
import com.fpt.swp391.model.TransactionInfo;
import com.fpt.swp391.model.TransactionWrapper;
import com.fpt.swp391.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/check-bank")
public class CheckBankController {
    private final OrderService orderService;

    public CheckBankController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> checkBank() throws IOException {
        try {
            String url = "https://mb.olygon.pro";

            // Mở kết nối đến URL và tạo đối tượng BufferedReader
            URL jsonUrl = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(jsonUrl.openStream()));

            // Đọc dữ liệu từ URL và lưu trữ trong StringBuilder
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            reader.close();

            ObjectMapper objectMapper = new ObjectMapper();

            TransactionWrapper wrapper = objectMapper.readValue(jsonString.toString(), TransactionWrapper.class);
            List<TransactionInfo> transactionInfos = wrapper.getTransactionInfos();

            for (TransactionInfo ti: transactionInfos) {
                if(ti.getDescription().contains("SWPORDER")) {
                    Long oid = Long.parseLong(ti.getDescription().split("SWPORDER")[1]);
                    Order o = orderService.getOrderbyId(oid);
                    if(o != null) {
                        if(o.getTotalPrice() <= Float.parseFloat(ti.getAmount())) {
                            orderService.updateOrderStatus(o.getId(), StatusEnum.DONE);
                        }
                    }
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body("Update Done!");
        } catch (Exception e) {

        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error");
    }
}
