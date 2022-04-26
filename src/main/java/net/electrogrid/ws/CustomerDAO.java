package net.electrogrid.ws;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

private static CustomerDAO instance;
	
	private static Connection connection;
	private PreparedStatement preparedStatement;
	
	private CustomerDAO() {};
	
	public static CustomerDAO getInstance() {
		if(instance == null) {
			instance = new CustomerDAO();
		}
		return instance;
	}
	
	public List<Customer> ListAll(){
		List<Customer> customers = new ArrayList<Customer>();
		
		try {
			connection  = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("select * from customer");
			
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				int customerId = rs.getInt("customerId");
				String nic = rs.getString("nic");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String gender = rs.getString("gender");
				int age = rs.getInt("age");
				String email = rs.getString("email");
			
				Customer customer = new Customer(customerId, nic, name, address, phone, gender, age, email);
				customers.add(customer);		
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return customers;
	}
	
	
	public int add(Customer customer) {
		
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("insert into customer(nic, name, address, phone, gender, age, email) values(?, ?, ?, ?, ?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, customer.getNic());
			preparedStatement.setString(2, customer.getName());
			preparedStatement.setString(3, customer.getAddress());
			preparedStatement.setString(4, customer.getPhone());
			preparedStatement.setString(5, customer.getGender());
			preparedStatement.setInt(6, customer.getAge());
			preparedStatement.setString(7, customer.getEmail());
			
			// Add Product
			int affectedRows = preparedStatement.executeUpdate();
			
			if (affectedRows == 0) {
	            throw new SQLException("Adding customer failed, no rows affected.");
	        }

	        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                return (int) generatedKeys.getLong(1);
	            }
	            else {
	                throw new SQLException("Adding customer failed, no ID obtained.");
	            }
	        }
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			/*
			 * Close prepared statement and database connectivity at the end of
			 * transaction
			 */
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return 0;
	}
	
	public Customer get(int id) {
		Customer customer = null;
		
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("select * from customer where customerId= ?");
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int customerId = rs.getInt("customerId");
				String nic = rs.getString("nic");
				String name = rs.getString("tax");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String gender = rs.getString("gender");
				int age = rs.getInt("age");
				String email = rs.getString("email");
			
				customer = new Customer(customerId, nic, name, address, phone, gender, age, email);

			}
				
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/*
			 * Close prepared statement and database connectivity at the end of
			 * transaction
			 */
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
		return customer;
	}
	
	public boolean update(Customer customer) {
		
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("Update customer set nic =?, name =?,address =?,phone=?, gender=?,age=?,email=? Where customerId =? ");
			
			
			preparedStatement.setString(1, customer.getNic());
			preparedStatement.setString(2, customer.getName());
			preparedStatement.setString(3, customer.getAddress());
			preparedStatement.setString(4, customer.getPhone());
			preparedStatement.setString(5, customer.getGender());
			preparedStatement.setInt(6, customer.getAge());
			preparedStatement.setString(7, customer.getEmail());
			preparedStatement.setInt(8, customer.getCustomerId());
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows==1) {
				return true;
			}		
			//preparedStatement.executeUpdate();	
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return false;
	}
	
	public boolean delete(int id) {
		
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("delete from customer where customerId = ?");
			preparedStatement.setInt(1, id);			
			
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows==1) {
				return true;
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return false;
		}
	
}
