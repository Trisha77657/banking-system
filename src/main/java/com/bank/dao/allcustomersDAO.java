package com.bank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class allcustomersDAO {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3307/bank_db";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "root";

    // SQL query to fetch customers excluding balance and password columns
    private static final String SELECT_CUSTOMERS_QUERY = "SELECT customer_id, full_name, address,mobile_no,email_id,account_type,date_of_birth,id_proof,account_no,status FROM customer";

    // Method to get all customers excluding balance and password
    public List<allcustomersDAO> getAllCustomersExcludingSensitiveData() {
        List<allcustomersDAO> customers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            stmt = conn.prepareStatement(SELECT_CUSTOMERS_QUERY);
            rs = stmt.executeQuery();

            while (rs.next()) {
                allcustomersDAO customer = new allcustomersDAO();
                allcustomersDAO.setId(rs.getInt("customer_id"));
                customer.setFirstName(rs.getString("full_name"));
                customer.setaddress(rs.getString("address"));
                customer.setmobileno(rs.getString("mobile_no"));
                customer.setEmail(rs.getString("email_id"));
                customer.setacctype(rs.getString("account_type"));
                customer.setdob(rs.getString("date_of_birth"));
                customer.setidproof(rs.getString("id_proof"));
                customer.setaccno(rs.getString("account_no"));
                customer.setstatus(rs.getString("status"));
                
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return customers;
    }

	private String setdob(String dob) {
		return dob;
		
	}

	private String setstatus(String status) {
		// TODO Auto-generated method stub
		return status;
	}

	private String setaccno(String accno) {
		// TODO Auto-generated method stub
		return accno;
	}

	private String setidproof(String idproof) {
		// TODO Auto-generated method stub
		return idproof;
	}

	private String setacctype(String acctype) {
		// TODO Auto-generated method stub
		return acctype;
	}

	private String setEmail(String Email) {
		// TODO Auto-generated method stub
		return Email;
	}

	private String setmobileno(String mobileno) {
		// TODO Auto-generated method stub
		return mobileno;
	}

	private String setaddress(String address) {
		// TODO Auto-generated method stub
		return address;
	}

	private static int setId(int int1) {
		// TODO Auto-generated method stub
		return int1;
	}

	private String setFirstName(String firstname) {
		// TODO Auto-generated method stub
		return firstname;
	}

	
	}

