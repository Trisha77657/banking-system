package com.bank.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bank.util.DBConnection;

/**
 * Servlet implementation class DepositNewServlet
 */
@WebServlet("/DepositNewServlet")
public class DepositNewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		String type=(String)request.getParameter("type");
		String accountNo=(String)session.getAttribute("customerAccountNo");
		double amount=Double.parseDouble(request.getParameter("amount"));
		try {
			Connection conn=DBConnection.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps=conn.prepareStatement("select initial_balance from customer where account_no=? for update");
			ps.setString(1, accountNo);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				double currentBalance=rs.getDouble("initial_balance");
				if(type.equals("Deposit")) {
					double newbalance=currentBalance+amount;
					ps=conn.prepareStatement("update customer set initial_balance=? where account_no=?");
					ps.setDouble(1, newbalance);
					ps.setString(2, accountNo);
					ps.executeUpdate();
					ps=conn.prepareStatement("insert into transactions (account_number,transaction_type,amount) values(?,?,?)");
					ps.setString(1, accountNo);
					ps.setString(2, type);
					ps.setDouble(3, amount);
					ps.executeUpdate();
					conn.commit();
					response.sendRedirect("customer_dashboard.jsp?succes=Transaction successful");
				}
				else if(type.equals("withdraw")&&currentBalance>amount) {
					double newbalance=currentBalance-amount;
					ps=conn.prepareStatement("update customer set initial_balance=? where account_no=?");
					ps.setDouble(1, newbalance);
					ps.setString(2, accountNo);
					ps.executeUpdate();
					ps=conn.prepareStatement("insert into transactions (account_number,transaction_type,amount) values(?,?,?)");
					ps.setString(1, accountNo);
					ps.setString(2, type);
					ps.setDouble(3, amount);
					ps.executeUpdate();
					conn.commit();
					response.sendRedirect("customer_dashboard.jsp?succes=Transaction successful");
				}
			}else {
				response.sendRedirect("customer_dashboard.jsp?error=account not found");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	

}
