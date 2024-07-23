<!-- modify_customer.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Modify Customer</title>
    <style>
        /* Add CSS styles here */
    </style>
</head>
<body>
    <h1>Modify Customer</h1>
    <form action="CustomerManagement2Servlet" method="post">
        <input type="hidden" name="action" value="modify">
        <input type="hidden" name="customerId" value="<%= request.getParameter("customerId") %>">
        
        <label>Full Name:</label>
        <input type="text" name="fullName" value="<%= request.getParameter("fullName") %>" required><br>
        
        <label>Address:</label>
        <input type="text" name="address" value="<%= request.getParameter("address") %>" required><br>
        
        <label>Mobile No:</label>
        <input type="text" name="mobileNo" value="<%= request.getParameter("mobileNo") %>" required><br>
        
        <label>Email ID:</label>
        <input type="email" name="emailId" value="<%= request.getParameter("emailId") %>" required><br>
        
        <label>Type of Account:</label>
        <select name="accountType">
            <option value="Saving" <%= request.getParameter("accountType").equals("Saving") ? "selected" : "" %>>Saving</option>
            <option value="Current" <%= request.getParameter("accountType").equals("Current") ? "selected" : "" %>>Current</option>
        </select><br>
        
        <label>Initial Balance:</label>
        <input type="number" name="initialBalance" min="1000" value="<%= request.getParameter("initialBalance") %>" required><br>
        
        <label>Date of Birth:</label>
        <input type="date" name="dateOfBirth" value="<%= request.getParameter("dateOfBirth") %>" required><br>
        
        <label>ID Proof:</label>
        <input type="text" name="idProof" value="<%= request.getParameter("idProof") %>" required><br>
        
        <button type="submit">Update</button>
    </form>
    <a href="viewCustomers.jsp">Back to Customer List</a>
</body>
</html>
