package com.example.feeservice.events;
public class FeePaymentEvent {

    private String studentId;
    private String feeType;
    private double amount;
    private String receiptReference;

   

    public String getStudentId() {
		return studentId;
	}



	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}



	public String getFeeType() {
		return feeType;
	}



	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}



	public double getAmount() {
		return amount;
	}



	public void setAmount(double amount) {
		this.amount = amount;
	}



	public String getReceiptReference() {
		return receiptReference;
	}



	public void setReceiptReference(String receiptReference) {
		this.receiptReference = receiptReference;
	}



	public FeePaymentEvent(String studentId, String feeType, double amount, String receiptReference) {
        this.studentId = studentId;
        this.feeType = feeType;
        this.amount = amount;
        this.receiptReference = receiptReference;
    }
}
