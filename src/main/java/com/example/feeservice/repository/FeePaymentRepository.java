package com.example.feeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.feeservice.entity.FeePayment;

@Repository
public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {}

