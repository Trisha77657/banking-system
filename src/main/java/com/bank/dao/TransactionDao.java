package com.bank.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.model.Transaction;

public class TransactionDao {
    
    private static final String JDBC_URL = "jdbc:mysql://localhost:3307/bank_db";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "root";

    
    private static final String SELECT_LAST_10_TRANSACTIONS = "SELECT * FROM transactions ORDER BY transaction_date DESC LIMIT 10";
    private static final String INSERT_TRANSACTION = "INSERT INTO transactions (account_no, amount, transaction_type) VALUES (?, ?, ?)";

    
   
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    
    public List<Transaction> getLast10Transactions() {
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_LAST_10_TRANSACTIONS);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(rs.getInt("id"));
                transaction.setAccountNo(rs.getString("account_no"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setTransactionType(rs.getString("transaction_type"));
                transaction.setTransactionDate(rs.getTimestamp("transaction_date"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return transactions;
    }

   
    public void insertTransaction(String accountNumber, double amount, String transactionType) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_TRANSACTION)) {

            pstmt.setString(1, accountNumber);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, transactionType);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    
    public void addTransaction(Transaction transaction) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_TRANSACTION)) {

            pstmt.setString(1, transaction.getAccountNo());
            pstmt.setDouble(2, transaction.getAmount());
            pstmt.setString(3, transaction.getTransactionType());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }
}