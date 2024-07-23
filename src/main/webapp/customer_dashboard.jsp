<%@page import="com.bank.util.DBConnection"%>
<%@ page import="java.sql.*, javax.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            text-align: center;
            background-color: #fff;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #333;
            margin-bottom: 20px;
        }
        h2 {
            color: #007bff;
            margin-bottom: 20px;
        }
        button {
            padding: 10px 20px;
            margin: 10px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Customer Dashboard</h1>
        <h2>Account Balance: 
            <%
            String accountNo = (String) session.getAttribute("customerAccountNo");
            String dbURL = "jdbc:mysql://localhost:3307/bank_db";
            String dbUser = "root";
            String dbPass = "root";

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DBConnection.getConnection(); 
                String query = "SELECT initial_balance FROM customer WHERE account_no = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, accountNo);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    out.print(rs.getString("initial_balance"));
                } else {
                    out.print("Account not found");
                }

                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            %>
        </h2>
        <button onclick="window.location.href='view_transactions.jsp'">View Transactions</button>
        <button onclick="window.location.href='depositnew.jsp'">Deposit</button>
        <button onclick="window.location.href='withdraw.jsp'">Withdraw</button><br>
        <button onclick="window.location.href='change_password.jsp'">Change Password</button>
    </div>
</body>
</html>
