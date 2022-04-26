package net.electrogrid.ws;

public class Bill {

	private int billId;
	private int unit;
	private int tax;
	private String billNo;
	private int amount;
	private int customerId;
	
	
	
	public Bill() {
		super();
	}
	
	public Bill(int billId, int unit, int tax, String billNo, int amount, int customerId) {
		super();
		this.billId = billId;
		this.unit = unit;
		this.tax = tax;
		this.billNo = billNo;
		this.amount = amount;
		this.customerId = customerId;
	}

	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
	public int getUnit() {
		return unit;
	}
	public void setUnit(int unit) {
		this.unit = unit;
	}
	public int getTax() {
		return tax;
	}
	public void setTax(int tax) {
		this.tax = tax;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	
	
	
	
	
}
