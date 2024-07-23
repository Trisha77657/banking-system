package com.bank.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.util.DBConnection;

@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // JDBC URL, username and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3307/bank_db";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "root";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accountNumber = request.getParameter("account_no");
        double amount = Integer.valueOf(request.getParameter("amount"));

        // Perform deposit transaction
//        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)
        try  {
        	Connection conn=DBConnection.getConnection();
            String sql = "INSERT INTO transactions (account_no, amount) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
                pstmt.setString(1, accountNumber);
                pstmt.setDouble(2, amount);
                pstmt.executeUpdate();
        }
         catch (SQLException e) {
            throw new ServletException("Error processing deposit", e);
        }

        // Redirect back to some confirmation page or main page
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    
        
}
}
