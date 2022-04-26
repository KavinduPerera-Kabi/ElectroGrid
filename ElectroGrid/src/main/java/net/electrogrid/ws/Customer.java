package net.electrogrid.ws;

public class Customer {

	private int customerId;
	private String nic;
	private String name;
	private String address;
	private String phone;
	private String gender;
	private int age;
	private String email;
	
	
	
	
	public Customer() {
		super();
	}

	public Customer(int customerId, String nic, String name, String address, String phone, String gender, int age,
			String email) {
		super();
		this.customerId = customerId;
		this.nic = nic;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.gender = gender;
		this.age = age;
		this.email = email;
	}
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getNic() {
		return nic;
	}
	public void setNic(String nic) {
		this.nic = nic;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}
