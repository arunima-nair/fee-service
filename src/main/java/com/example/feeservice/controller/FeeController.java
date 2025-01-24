package com.example.feeservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.feeservice.entity.Receipt;
import com.example.feeservice.service.FeePaymentService;

@RestController
@RequestMapping("/fees")
public class FeeController {
    @Autowired
    private FeePaymentService feeService;

    @PostMapping("/collect")
    public ResponseEntity<Receipt> collectFee(
            @RequestParam String studentId,
            @RequestParam String feeType,
            @RequestParam double amount) {
        Receipt receipt = feeService.collectFeeAndGenerateReceipt(studentId, feeType, amount);
        return ResponseEntity.ok(receipt);
    }
    
}
