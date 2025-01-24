package com.example.feeservice.service;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.feeservice.client.StudentServiceClient;
import com.example.feeservice.dto.StudentDTO;
import com.example.feeservice.entity.FeePayment;
import com.example.feeservice.entity.Receipt;
//import com.example.feeservice.events.FeePaymentEvent;
import com.example.feeservice.exception.StudentNotFoundException;
import com.example.feeservice.repository.FeePaymentRepository;
import com.example.feeservice.repository.ReceiptRepository;

import feign.FeignException;

@Service
public class FeePaymentService {

    @Autowired
    private FeePaymentRepository feePaymentRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private StudentServiceClient studentServiceClient;
    
   // @Autowired
   // private KafkaTemplate<String, FeePaymentEvent> kafkaTemplate;

    private static final String FEE_PAYMENT_TOPIC = "fee-payment-topic"; // Topic for fee payment events

    /**
     * Collects a fee for a student and generates a receipt if it doesn't exist.
     *
     * @param studentId The ID of the student paying the fee.
     * @param feeType   The type of fee (e.g., Tuition Fees).
     * @param amount    The amount paid.
     * @return The generated receipt containing the fee payment details.
     */
    public Receipt collectFeeAndGenerateReceipt(String studentId, String feeType, double amount) {
    	StudentDTO student;
        try {
        	    student = studentServiceClient.getStudentById(Long.valueOf(studentId));
        } catch (FeignException.NotFound e) { // Handle 404 from Feign Client
            throw new StudentNotFoundException("Student with ID " + studentId + " not found.");
        }

        if (student == null) {
            throw new StudentNotFoundException("Student with ID " + studentId + " not found.");
        }
        Receipt receipt = receiptRepository.findByStudentIdAndTransactionDate(studentId, LocalDateTime.now());

        if (receipt == null) {

            receipt = new Receipt();
            receipt.setStudentName(student.getName()); 
            receipt.setStudentId(student.getId());
            receipt.setReferenceNumber("REF" + System.currentTimeMillis());
            receipt.setCardNumber("XXXX-XXXX-XXXX-1234"); 
            receipt.setCardType("MasterCard");
            receipt.setTransactionDate(LocalDateTime.now());
            receipt.setTotalAmount(0.0); 
            receipt = receiptRepository.save(receipt);
        }

        FeePayment feePayment = new FeePayment();
        feePayment.setStudentId(studentId);
        feePayment.setFeeType(feeType);
        feePayment.setAmount(amount);
        feePayment.setPaymentDate(LocalDateTime.now());
        feePayment.setReceipt(receipt);

        feePaymentRepository.save(feePayment);

        receipt.setTotalAmount(receipt.getTotalAmount() + amount);
        receipt = receiptRepository.save(receipt);
      //  FeePaymentEvent feePaymentEvent = new FeePaymentEvent(studentId, feeType, amount, receipt.getReferenceNumber());
       // kafkaTemplate.send(FEE_PAYMENT_TOPIC, feePaymentEvent);

        return receipt;
    }
}
