package com.bank.dao;

import com.bank.model.Customer;
import com.bank.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public void registerCustomer(Customer customer) {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Customer(full_name, address, mobile_no, email_id, account_type, initial_balance, date_of_birth, id_proof, account_no, password, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, customer.getFullName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getMobileNo());
            ps.setString(4, customer.getEmailId());
            ps.setString(5, customer.getAccountType());
            ps.setDouble(6, customer.getInitialBalance());
            ps.setDate(7, customer.getDateOfBirth());
            ps.setString(8, customer.getIdProof());
            ps.setString(9, customer.getAccountNo());
            ps.setString(10, customer.getPassword());
            ps.setString(11, customer.getStatus());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM customer");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setFullName(rs.getString("full_name"));
                customer.setAddress(rs.getString("address"));
                customer.setMobileNo(rs.getString("mobile_no"));
                customer.setEmailId(rs.getString("email_id"));
                customer.setAccountType(rs.getString("account_type"));
                customer.setInitialBalance(rs.getDouble("initial_balance"));
                customer.setDateOfBirth(rs.getDate("date_of_birth"));
                customer.setIdProof(rs.getString("id_proof"));
                customer.setAccountNo(rs.getString("account_no"));
                customer.setPassword(rs.getString("password"));
                customer.setStatus(rs.getString("status"));
                customers.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }
    public Customer getCustomerByAccountNoAndPassword(String accountNo, String password) {
        Customer customer = null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM customer WHERE account_no = ? AND password = ?");
            ps.setString(1, accountNo);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setFullName(rs.getString("full_name"));
                customer.setAddress(rs.getString("address"));
                customer.setMobileNo(rs.getString("mobile_no"));
                customer.setEmailId(rs.getString("email_id"));
                customer.setAccountType(rs.getString("account_type"));
                customer.setInitialBalance(rs.getDouble("initial_balance"));
                customer.setDateOfBirth(rs.getDate("date_of_birth"));
                customer.setIdProof(rs.getString("id_proof"));
                customer.setAccountNo(rs.getString("account_no"));
                customer.setPassword(rs.getString("password"));
                customer.setStatus(rs.getString("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    public boolean updateBalance(String accountNo, double amount, String transactionType) {
        boolean success = false;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE customer SET initial_balance = initial_balance + ? WHERE account_no = ? AND status = 'Active'");
            ps.setDouble(1, amount);
            ps.setString(2, accountNo);
            int rowsUpdated = ps.executeUpdate();
            success = rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public void closeAccount(String accountNo) {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE customer SET status = 'Closed' WHERE account_no = ?");
            ps.setString(1, accountNo);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateCustomer(Customer customer) {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                "UPDATE customer SET full_name = ?, address = ?, mobile_no = ?, email_id = ?, account_type = ?, date_of_birth = ?, id_proof = ? WHERE customer_id = ?"
            );
            ps.setString(1, customer.getFullName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getMobileNo());
            ps.setString(4, customer.getEmailId());
            ps.setString(5, customer.getAccountType());
            ps.setDate(6, customer.getDateOfBirth());
            ps.setString(7, customer.getIdProof());
            ps.setInt(8, customer.getCustomerId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    
