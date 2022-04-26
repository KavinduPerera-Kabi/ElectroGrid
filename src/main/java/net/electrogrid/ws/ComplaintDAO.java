package net.electrogrid.ws;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAO {

	private static ComplaintDAO instance;
	
	private static Connection connection;
	private PreparedStatement preparedStatement;
	
	private ComplaintDAO() {};
	
	public static ComplaintDAO getInstance() {
		if(instance == null) {
			instance = new ComplaintDAO();
		}
		return instance;
	}
	
	public List<Complaint> ListAll(){
		List<Complaint> complaints = new ArrayList<Complaint>();

		try {
			connection  = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("select * from complaint");

			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {

				int comId = rs.getInt("comId");
				String customerName = rs.getString("customerName");
				String complaint = rs.getString("complaint");
				int customerId = rs.getInt("customerId");
				int employeeId = rs.getInt("employeeId");

				Complaint complaintDetail = new Complaint(comId, customerName, complaint,customerId,employeeId);
				complaints.add(complaintDetail);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return complaints;
	}
	
	public int add(Complaint complaint) {

		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("insert into complaint(customerName, complaint,customerId,employeeId) values(?, ?,?,?)",
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, complaint.getCustomerName());
			preparedStatement.setString(2, complaint.getComplaint());
			preparedStatement.setInt(3, complaint.getCustomerId());
			preparedStatement.setInt(4, complaint.getEmployeeId());


			// Add Product
			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Adding complaint failed, no rows affected.");
			}

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return (int) generatedKeys.getLong(1);
				}
				else {
					throw new SQLException("Adding complaint failed, no ID obtained.");
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
	
	public Complaint get(int id) {
		Complaint complaintDetail = null;
		
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("select * from complaint where comId= ?");
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int comId = rs.getInt("comId");
				String customerName = rs.getString("customerName");
				String complaint = rs.getString("complaint");
				int customerId = rs.getInt("customerId");
				int employeeId = rs.getInt("employeeId");

				complaintDetail = new Complaint(comId, customerName, complaint,customerId,employeeId);
				

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
		
		
		return complaintDetail;
	}
	
	public boolean update(Complaint complaint) {
		
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("Update complaint set customerName =?, complaint = ?, customerId = ?,employeeId = ? where comId = ?  ");
				
			preparedStatement.setString(1, complaint.getCustomerName());
			preparedStatement.setString(2, complaint.getComplaint());
			preparedStatement.setInt(3, complaint.getCustomerId());
			preparedStatement.setInt(4, complaint.getEmployeeId());
			preparedStatement.setInt(5, complaint.getComId());


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
			preparedStatement = connection.prepareStatement("delete from complaint where comId = ?");
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
