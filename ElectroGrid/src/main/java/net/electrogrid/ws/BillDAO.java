package net.electrogrid.ws;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class BillDAO {
	
	private static BillDAO instance;
	
	private static Connection connection;
	private PreparedStatement preparedStatement;
	
	private BillDAO() {};
	
	public static BillDAO getInstance() {
		if(instance == null) {
			instance = new BillDAO();
		}
		return instance;
	}
	
	public List<Bill> ListAll(){
		List<Bill> bills = new ArrayList<Bill>();
		
		try {
			connection  = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("select * from bill");
			
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				int billId = rs.getInt("billId");
				int unit = rs.getInt("unit");
				int tax = rs.getInt("tax");
				String billNo = rs.getString("billNo");
				int amount = rs.getInt("amount");
				int customerId = rs.getInt("customerId");
			
				Bill bill = new Bill(billId, unit, tax, billNo, amount, customerId);
				bills.add(bill);		
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return bills;
	}
	
	public int add(Bill bill) {
		
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("insert into bill(unit, tax, billNo, amount,customerId) values(?, ?, ?, ?,?)", 
					Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setInt(1, bill.getUnit());
			preparedStatement.setInt(2, bill.getTax());
			preparedStatement.setString(3, bill.getBillNo());
			preparedStatement.setInt(4, bill.getAmount());
			preparedStatement.setInt(5, bill.getCustomerId());
			
			// Add Product
			int affectedRows = preparedStatement.executeUpdate();
			
			if (affectedRows == 0) {
	            throw new SQLException("Adding bill failed, no rows affected.");
	        }

	        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                return (int) generatedKeys.getLong(1);
	            }
	            else {
	                throw new SQLException("Adding bill failed, no ID obtained.");
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
	
	public Bill get(int id) {
		Bill bill = null;
		
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("select * from bill where billId= ?");
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int billId = rs.getInt("billId");
				int unit = rs.getInt("unit");
				int tax = rs.getInt("tax");
				String billNo = rs.getString("billNo");
				int amount = rs.getInt("amount");
				int customerId = rs.getInt("customerId");
				
				bill = new Bill(billId, unit, tax, billNo, amount,customerId);
				

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
		
		
		return bill;
	}
	
	public boolean update(Bill bill) {
		
		try {
			connection = DatabaseUtils.getConnection();
			preparedStatement = connection.prepareStatement("Update bill set unit =?, tax = ?, billNo =?, amount =? ,customerId = ? where billId = ? ");
				
			preparedStatement.setInt(1, bill.getUnit());
			preparedStatement.setInt(2, bill.getTax());
			preparedStatement.setString(3, bill.getBillNo());
			preparedStatement.setInt(4, bill.getAmount());
			preparedStatement.setInt(5, bill.getCustomerId());
			preparedStatement.setInt(6, bill.getBillId());


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
			preparedStatement = connection.prepareStatement("delete from bill where billId = ?");
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
