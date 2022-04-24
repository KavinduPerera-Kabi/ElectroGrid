package net.electrogrid.ws;


public class Complaint {

	private int comId;
	private String customerName;
	private String complaint;
	private int customerId;
	private int employeeId;
	
	public Complaint() {
		super();
	}
	
	
	
	public Complaint(int comId, String customerName, String complaint, int customerId, int employeeId) {
		super();
		this.comId = comId;
		this.customerName = customerName;
		this.complaint = complaint;
		this.customerId = customerId;
		this.employeeId = employeeId;
	}



	public int getComId() {
		return comId;
	}
	public void setComId(int comId) {
		this.comId = comId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getComplaint() {
		return complaint;
	}
	public void setComplaint(String complaint) {
		this.complaint = complaint;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
	
	
}
