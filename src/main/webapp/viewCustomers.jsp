<%@page import="com.bank.util.DBConnection"%>
<%@ page language="java" import="java.sql.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Customers</title>
</head>

<body>
<%
if(session==null || session.getAttribute("adminUsername") == null){
    response.sendRedirect("admin_dashboard.jsp?message=you dont't have access");
    return ;
}
%>

<h2>Customer List</h2>
<table border="1">
    <tr>
        <th>Name</th>
        <th>Address</th>
        <th>Mobile No</th>
        <th>Email</th>
        <th>Account Type</th>
        <th>DOB</th>
        <th>Proof</th>
        <th>Action</th>
<%
try{
    Connection conn = DBConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("select * from customer");
    ResultSet rs = ps.executeQuery();
    while(rs.next()){
    	System.out.println(rs.getInt("customer_id"));
%>
    <tr>
        <td><%= rs.getString("full_name") %></td>
        <td><%= rs.getString("address") %></td>
        <td><%= rs.getString("mobile_no") %></td>
        <td><%= rs.getString("email_id") %></td>
        <td><%= rs.getString("account_type") %></td>
        <td><%= rs.getString("date_of_birth") %></td>
        <td><%= rs.getString("id_proof") %></td>
        <td>
            <!-- Modify form -->
            <form action="CustomerManagement2Servlet" method="post">
                <input type="hidden" name="customerId" value="<%= rs.getInt("customer_id") %>">
                
                <input type="hidden" name="action" value="modify">
                <input type="submit" value="Modify">
            </form>
            <!-- Delete form -->
            <form action="CustomerManagentServlet" method="post">
                <input type="hidden" name="customerId" value="<%= rs.getInt("customer_id") %>">
                
                <input type="hidden" name="account_no" value="<%= rs.getString("account_no") %>">
                <input type="hidden" name="action" value="delete">
                <input type="submit" value="Delete">
            </form>
        </td>
    </tr>
<%
    }
   
    rs.close();
    ps.close();
    conn.close();
} catch(Exception e){
    e.printStackTrace();
}
%>
</body>
</html>