package com.bank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bank.util.DBConnection;

public class ChangePasswordDAOImpl implements ChangePasswordDAO {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3307/bank_db";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "root";

    @Override
    public boolean verifyOldPassword(int accountNo, String oldPassword) {
        boolean passwordMatch = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            String query = "SELECT password FROM customer WHERE account_no = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, accountNo);
            rs = ps.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                // Replace with proper password hashing comparison logic
                if (storedPassword.equals(oldPassword)) {
                    passwordMatch = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return passwordMatch;
    }

    @Override
    public boolean updatePassword(int accountNo, String newPassword) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Disable auto-commit mode

            

            String query = "UPDATE customer SET password = ? WHERE account_no = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, newPassword);
            ps.setInt(2, accountNo);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                success = true;
                conn.commit(); // Commit transaction if update is successful
            } else {
                conn.rollback(); // Rollback if no rows were updated
            }
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); // Rollback on exception
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) {
                    conn.setAutoCommit(true); // Restore auto-commit mode
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }
}