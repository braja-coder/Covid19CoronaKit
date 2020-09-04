package com.coronakit.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class KitDao {

	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;

	public KitDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

	protected void connect() throws SQLException {
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}
	}

	protected void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}

	
	public boolean addNewVisitor(String name, String email, String phone) throws ClassNotFoundException, SQLException {
		String sql = "insert into user (userName,userEmail,phoneNumber) values(?,?,?)";
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		pstmt.setString(1, name);
		pstmt.setString(2, email);
		pstmt.setInt(3, Integer.parseInt(phone));
		
		boolean added = pstmt.executeUpdate() > 0;
		
		pstmt.close();
		this.disconnect();
		return added;
	}
	
	public Integer addCoronakit(String personName, String email, String contactNumber,int totalAmount,String deliveryAddress,String orderDate,boolean orderFinalized) throws ClassNotFoundException, SQLException {
		
		String sql = "insert into coronaKit (pPersonName,pEmail,pContactNumber,pTotalAmount,pDeliveryAddress,pOrderDate,pOrderFinalized) values(?,?,?,?,?,?,?)";
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		pstmt.setString(1, personName);
		pstmt.setString(2, email);
		pstmt.setString(3, contactNumber);
		pstmt.setInt(4, totalAmount);
		pstmt.setString(5, deliveryAddress);
		pstmt.setString(6, orderDate);
		pstmt.setBoolean(7, orderFinalized);
		
		boolean added = pstmt.executeUpdate() > 0;
		
		sql = "select id from coronaKit";
		
		Statement stmt = this.jdbcConnection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		int coronkitid=0;
		
		while(rs.next())  {
			
			coronkitid=rs.getInt("id");
		}
		pstmt.close();
		this.disconnect();
		return coronkitid;
	}
	
	public boolean addKitdetails(int coronaKitId, int productId, int quantity,int amount) throws ClassNotFoundException, SQLException {
		
		String sql = "insert into kitdetails (pcoronaKitId,pproductId,pquantity,pamount) values(?,?,?,?)";
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		pstmt.setInt(1,coronaKitId );
		pstmt.setInt(2, productId);
		pstmt.setInt(3, quantity);
		pstmt.setInt(4, amount);
		
		boolean added = pstmt.executeUpdate() > 0;
		
		pstmt.close();
		this.disconnect();
		return added;
	}
}