package com.example.reciept.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.feeservice.entity.Receipt;
import com.example.feeservice.repository.ReceiptRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;

    /**
     * Creates a new receipt and saves it to the database.
     *
     * @param receipt The receipt to be created.
     * @return The saved receipt.
     */
    public Receipt createReceipt(Receipt receipt) {
        return receiptRepository.save(receipt);
    }

    /**
     * Retrieves a receipt by its ID.
     *
     * @param id The ID of the receipt to retrieve.
     * @return The receipt if found, or throws an exception if not found.
     */
    public Receipt getReceiptById(Long id) {
        return receiptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receipt not found for ID: " + id));
    }

    /**
     * Retrieves all receipts.
     *
     * @return A list of all receipts in the database.
     */
    public List<Receipt> getAllReceipts() {
        return receiptRepository.findAll();
    }

    /**
     * Updates an existing receipt.
     *
     * @param id      The ID of the receipt to update.
     * @param receipt The updated receipt data.
     * @return The updated receipt.
     */
    public Receipt updateReceipt(Long id, Receipt receipt) {
        Optional<Receipt> existingReceipt = receiptRepository.findById(id);

        if (existingReceipt.isPresent()) {
            Receipt updatedReceipt = existingReceipt.get();
            updatedReceipt.setStudentName(receipt.getStudentName());
            updatedReceipt.setStudentId(receipt.getStudentId());
            updatedReceipt.setReferenceNumber(receipt.getReferenceNumber());
            updatedReceipt.setCardNumber(receipt.getCardNumber());
            updatedReceipt.setCardType(receipt.getCardType());
            updatedReceipt.setTotalAmount(receipt.getTotalAmount());
            updatedReceipt.setTransactionDate(receipt.getTransactionDate());
            return receiptRepository.save(updatedReceipt);
        } else {
            throw new RuntimeException("Receipt not found for ID: " + id);
        }
    }

    /**
     * Deletes a receipt by its ID.
     *
     * @param id The ID of the receipt to delete.
     */
    public void deleteReceipt(Long id) {
        receiptRepository.deleteById(id);
    }
}

