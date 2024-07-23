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
 * Servlet implementation class CustomerManagentServlet
 */
@WebServlet("/CustomerManagentServlet")
public class CustomerManagentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);
		if(session==null||session.getAttribute("adminUsername")==null) {
			response.sendRedirect("admin_login.jsp");
		}
		String action=request.getParameter("action");
		if(action.equals("delete")) {
			System.out.println("hello this is delete operation");
			try {
				String accountNo=request.getParameter("account_no");
				System.out.println(accountNo);
				Connection con=DBConnection.getConnection();
				 PreparedStatement ps=con.prepareStatement(" SET FOREIGN_KEY_CHECKS=0");
				 ps.executeUpdate();
				
				
			    ps=con.prepareStatement(" delete from customer where account_no=?");
				ps.setString(1, accountNo);
				ps.executeUpdate();
				con.close();
				response.sendRedirect("viewCustomers.jsp?message=success");
			}catch(Exception e) {
				e.printStackTrace();
				
			}
		
		}
		if(action.equals("modify")) {
			response.sendRedirect("modify_customer.jsp?message=success");
			
		}
	}
	

	
}
