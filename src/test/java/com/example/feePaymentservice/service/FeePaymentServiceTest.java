package com.example.feePaymentservice.service;
import com.example.feeservice.client.StudentServiceClient;
import com.example.feeservice.dto.StudentDTO;
import com.example.feeservice.entity.FeePayment;
import com.example.feeservice.entity.Receipt;
import com.example.feeservice.events.FeePaymentEvent;
import com.example.feeservice.exception.StudentNotFoundException;
import com.example.feeservice.repository.FeePaymentRepository;
import com.example.feeservice.repository.ReceiptRepository;
import com.example.feeservice.service.FeePaymentService;

import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FeePaymentServiceTest {

    @Mock
    private FeePaymentRepository feePaymentRepository;

    @Mock
    private ReceiptRepository receiptRepository;

    @Mock
    private StudentServiceClient studentServiceClient;

//    @Mock
//    private KafkaTemplate<String, FeePaymentEvent> kafkaTemplate;

    @InjectMocks
    private FeePaymentService feePaymentService;

    private StudentDTO studentDTO;
    private Receipt receipt;

    @BeforeEach
    public void setUp() {
        // Initialize mock studentDTO
        studentDTO = new StudentDTO();
        studentDTO.setId("1");
        studentDTO.setName("John Doe");

        // Initialize mock receipt
        receipt = new Receipt();
        receipt.setStudentId("1");
        receipt.setTotalAmount(0.0);
    }

    @Test
    void testCollectFeeAndGenerateReceipt_studentFound_receiptCreated() {
        when(studentServiceClient.getStudentById(1L)).thenReturn(studentDTO);
        when(receiptRepository.findByStudentIdAndTransactionDate(anyString(), any(LocalDateTime.class))).thenReturn(null);
        when(receiptRepository.save(any(Receipt.class))).thenReturn(receipt);
        when(feePaymentRepository.save(any(FeePayment.class))).thenReturn(new FeePayment());

        Receipt result = feePaymentService.collectFeeAndGenerateReceipt("1", "Tuition", 100.0);

        // Verify receiptRepository.save() is called twice
        verify(receiptRepository, times(2)).save(any(Receipt.class));

        // Assert the result
        assert(result != null);
        assert(result.getTotalAmount() == 100.0);
    }

    @Test
    public void testCollectFeeAndGenerateReceipt_studentNotFound() {
        // Mock the student service client to throw FeignException (404)
        when(studentServiceClient.getStudentById(anyLong())).thenThrow(FeignException.NotFound.class);

        // Call the service method and assert exception
        assertThrows(StudentNotFoundException.class, () -> 
            feePaymentService.collectFeeAndGenerateReceipt("999", "Tuition", 100.0)
        );

        // Verify that the student service client was called
        verify(studentServiceClient, times(1)).getStudentById(anyLong());
    }

    @Test
    void testCollectFeeAndGenerateReceipt_receiptExists() {
        // Mock the required dependencies
        when(studentServiceClient.getStudentById(1L)).thenReturn(studentDTO);
        // Return a pre-existing receipt for the student
        when(receiptRepository.findByStudentIdAndTransactionDate(anyString(), any(LocalDateTime.class)))
            .thenReturn(receipt);  // This ensures that a receipt is returned when queried
        // Mock save to return the same receipt when saved
        when(receiptRepository.save(any(Receipt.class))).thenReturn(receipt);
        when(feePaymentRepository.save(any(FeePayment.class))).thenReturn(new FeePayment());

        // Call the service method
        Receipt result = feePaymentService.collectFeeAndGenerateReceipt("1", "Tuition", 100.0);

        // Verify the result is not null
        assertNotNull(result, "Receipt should not be null");

        // Verify the save method was called once
        verify(receiptRepository, times(1)).save(any(Receipt.class));

        // Assert the expected values on the result
        assertEquals(100.0, result.getTotalAmount());
    }
}