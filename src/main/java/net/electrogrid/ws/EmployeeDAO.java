package net.electrogrid.ws;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {


	private static EmployeeDAO instance;
	
	private static Connection connection;
	private PreparedStatement preparedStatement;
	
	private EmployeeDAO() {};
	
	public static EmployeeDAO getInstance() {
		if(instance == null) {
			instance = new EmployeeDAO();
		}
		return instance;
	}
	
	public List<Employee> ListAll(){
		List<Employee> employees = new ArrayList<Employee>();
		
		try {
			connection  = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("select * from employee");
			
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				int employeeId = rs.getInt("employeeId");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String gender = rs.getString("gender");
				int age = rs.getInt("age");
				String email = rs.getString("email");

				Employee employee = new Employee(employeeId, name, address, phone, gender, age, email);
				employees.add(employee);		
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		
		return employees;
	}
	
	public int add(Employee employee) {
		
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("insert into employee( name, address, phone, gender, age, email) values(?, ?, ?, ?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, employee.getName());
			preparedStatement.setString(2, employee.getAddress());
			preparedStatement.setString(3, employee.getPhone());
			preparedStatement.setString(4, employee.getGender());
			preparedStatement.setInt(5, employee.getAge());
			preparedStatement.setString(6, employee.getEmail());
			
			// Add Product
			int affectedRows = preparedStatement.executeUpdate();
			
			if (affectedRows == 0) {
	            throw new SQLException("Adding employee failed, no rows affected.");
	        }

	        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                return (int) generatedKeys.getLong(1);
	            }
	            else {
	                throw new SQLException("Adding employee failed, no ID obtained.");
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
	
	public Employee get(int id) {
		Employee employee = null;
		
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("select * from employee where customerId= ?");
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int customerId = rs.getInt("customerId");
				String name = rs.getString("tax");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String gender = rs.getString("gender");
				int age = rs.getInt("age");
				String email = rs.getString("email");
			
				employee = new Employee(customerId, name, address, phone, gender, age, email);

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
		
	
		return employee;
	}
	
	public boolean update(Employee employee) {
		
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("Update employee set name = ?, address = ?, phone = ?, gender = ?, age = ?, email = ? where employeeId = ? ");
			
			preparedStatement.setString(1, employee.getName());
			preparedStatement.setString(2, employee.getAddress());
			preparedStatement.setString(3, employee.getPhone());
			preparedStatement.setString(4, employee.getGender());
			preparedStatement.setInt(5, employee.getAge());
			preparedStatement.setString(6, employee.getEmail());
			preparedStatement.setInt(7, employee.getEmployeeId());

			
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
			preparedStatement = connection.prepareStatement("delete from employee where employeeId = ?");
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
