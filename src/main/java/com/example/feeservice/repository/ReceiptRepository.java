package com.example.feeservice.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.feeservice.entity.Receipt;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    Receipt findByStudentIdAndTransactionDate(String studentId, LocalDateTime  transactionDate);

}
