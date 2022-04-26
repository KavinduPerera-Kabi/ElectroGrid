package net.electrogrid.ws;

public class Payment {
	
	private int paymentId;
	private String payOptions;
	private int totalPayment;
	private String cardName;
	private String customerName;
	private int customerId;
	
	
	public Payment() {
		super();
	}
	 
	public Payment(int paymentId, String payOptions, int totalPayment, String cardName, String customerName,
			int customerId) {
		super();
		this.paymentId = paymentId;
		this.payOptions = payOptions;
		this.totalPayment = totalPayment;
		this.cardName = cardName;
		this.customerName = customerName;
		this.customerId = customerId;
	}

	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	public String getPayOptions() {
		return payOptions;
	}
	public void setPayOptions(String payOptions) {
		this.payOptions = payOptions;
	}
	public int getTotalPayment() {
		return totalPayment;
	}
	public void setTotalPayment(int totalPayment) {
		this.totalPayment = totalPayment;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	

}
