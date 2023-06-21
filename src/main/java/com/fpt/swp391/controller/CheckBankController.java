package com.fpt.swp391.controller;

import com.fpt.swp391.model.TransactionInfo;
import com.fpt.swp391.service.CheckBankService;
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
    private final CheckBankService checkBankService;

    public CheckBankController(CheckBankService checkBankService) {
        this.checkBankService = checkBankService;
    }

    @GetMapping("/all")
    public List<TransactionInfo> checkBank() throws IOException {
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

        // Đóng kết nối
        reader.close();

        // Xử lý dữ liệu JSON tại đây (ví dụ: phân tích cú pháp, truy cập thuộc tính, ...)

        // In dữ liệu JSON đã đọc
        System.out.println(jsonString.toString());
        return null;
        //        return checkBankService.getTransactionsFromApi();
    }
}
