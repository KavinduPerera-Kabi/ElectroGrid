package net.electrogrid.ws;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {


	private static PaymentDAO instance;
	
	private static Connection connection;
	private PreparedStatement preparedStatement;
	
	private PaymentDAO() {};
	
	public static PaymentDAO getInstance() {
		if(instance == null) {
			instance = new PaymentDAO();
		}
		return instance;
	}
	
	public List<Payment> ListAll(){
		List<Payment> payments = new ArrayList<Payment>();
		
		try {
			connection  = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("select * from payment");
			
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				int paymentId = rs.getInt("paymentId");
				String payOptions = rs.getString("payOptions");
				int totalPayment = rs.getInt("totalPayment");
				String cardName = rs.getString("cardName");
				String customerName = rs.getString("customerName");
				int customerId = rs.getInt("customerId");
				

				Payment payment = new Payment(paymentId, payOptions, totalPayment, cardName, customerName, customerId);
				payments.add(payment);		
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		
		return payments;
	}
	
	public int add(Payment payment) {
		
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("insert into payment( payOptions, totalPayment, cardName, customerName,customerId) values(?, ?, ?, ?,?)", 
					Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, payment.getPayOptions());
			preparedStatement.setInt(2, payment.getTotalPayment());
			preparedStatement.setString(3, payment.getCardName());
			preparedStatement.setString(4, payment.getCustomerName());
			preparedStatement.setInt(5, payment.getCustomerId());
			
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
	
	public Payment get(int id) {
		Payment payment = null;
		
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("select * from payment where paymentId= ?");
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int paymentId = rs.getInt("paymentId");
				String payOptions = rs.getString("payOptions");
				int totalPayment = rs.getInt("totalPayment");
				String cardName = rs.getString("cardName");
				String customerName = rs.getString("customerName");
				int customerId = rs.getInt("customerId");
				
				payment = new Payment(paymentId, payOptions, totalPayment, cardName, customerName,customerId);

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
		
	
		return payment;
	}
	
	public boolean update(Payment payment) {
		
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("Update payment set payOptions = ?, totalPayment = ?, cardName = ?, customerName = ?, customerId =? where paymentId = ?  ");
			
			
			preparedStatement.setString(1, payment.getPayOptions());
			preparedStatement.setInt(2, payment.getTotalPayment());
			preparedStatement.setString(3, payment.getCardName());
			preparedStatement.setString(4, payment.getCustomerName());
			preparedStatement.setInt(5, payment.getCustomerId());
			preparedStatement.setInt(6, payment.getPaymentId());
			
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
			preparedStatement = connection.prepareStatement("delete from payment where paymentId = ?");
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
