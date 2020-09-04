package com.coronakit.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.coronakit.model.ProductMaster;



public class ProductMasterDao {

	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;

	public ProductMasterDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
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

	// add DAO methods as per requirements
	public boolean addProduct(String pname, String pcost, String pdescription) throws ClassNotFoundException, SQLException {
		String sql = "insert into productmaster (pName,pCost,pDescription) values(?,?,?)";
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		pstmt.setString(1, pname);
		pstmt.setString(2, pcost);
		pstmt.setString(3,pdescription );
		
		boolean added = pstmt.executeUpdate() > 0;
		
		pstmt.close();
		this.disconnect();
		return added;
	}
	
	public List<ProductMaster> getAllproductRecords() throws ClassNotFoundException, SQLException {
		String sql = "select * from productmaster";
		this.connect();
		
		Statement stmt = this.jdbcConnection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		// map it to model
		List<ProductMaster> productlist = new ArrayList<ProductMaster>();
		while(rs.next()) {
			ProductMaster product = new ProductMaster(rs.getInt("pid"), 
											 rs.getString("pName"), 
											 rs.getString("pCost"), 
											 rs.getString("pDescription"));
			productlist.add(product);		
		}
		
		rs.close();
		stmt.close();
		this.disconnect();
		
		return productlist;
	}
	
	public ProductMaster getproductRecord(String id) throws ClassNotFoundException, SQLException {
		
		String sql = "select * from productmaster where pid="+id;
		this.connect();
		
		Statement stmt = this.jdbcConnection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		ProductMaster product = null;
		
		// map it to model
		while(rs.next()) {
			
			 product = new ProductMaster(rs.getInt("pid"), 
											 rs.getString("pName"), 
											 rs.getString("pCost"), 
											 rs.getString("pDescription"));
		}
		rs.close();
		stmt.close();
		this.disconnect();
		
		return product;
	}	
	
	public boolean editProduct(String id,String pname,String pcost,String pdescription) throws ClassNotFoundException, SQLException {
		
		String sql = "update productmaster set pName=?,pCost=?,pDescription=? where pid=?";
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		
		pstmt.setString(1, pname);
		pstmt.setString(2, pcost);
		pstmt.setString(3,pdescription );
		pstmt.setInt(4, Integer.parseInt(id));
	
		boolean edit = pstmt.executeUpdate() > 0;
		
		pstmt.close();
		this.disconnect();
		return edit;
	}	
  public boolean deleteProduct(String id) throws ClassNotFoundException, SQLException {
		
		String sql = "delete from productmaster where pid=?";
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		pstmt.setInt(1, Integer.parseInt(id));
		
		boolean deleted = pstmt.executeUpdate() > 0;
		
		pstmt.close();
		this.disconnect();
		return deleted;
	}

}