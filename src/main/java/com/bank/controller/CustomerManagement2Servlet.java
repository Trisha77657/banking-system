package com.bank.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bank.util.DBConnection;

@WebServlet("/CustomerManagement2Servlet")
public class CustomerManagement2Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("modify".equals(action)) {
            try {
                // Retrieve and trim parameters
                String customerIdStr = getTrimmedParameter(request, "customerId");
                String initialBalanceStr = getTrimmedParameter(request, "initialBalance");

                // Check if mandatory fields are empty
                if (customerIdStr.isEmpty() || initialBalanceStr.isEmpty()) {
                    response.sendRedirect("viewCustomers.jsp?message=Customer ID and Initial Balance cannot be empty");
                    return;
                }

                // Parse parameters to appropriate data types
                int customerId = Integer.parseInt(customerIdStr);
                double initialBalance = Double.parseDouble(initialBalanceStr);

                // Retrieve and trim other parameters
                String fullName = getTrimmedParameter(request, "fullName");
                String address = getTrimmedParameter(request, "address");
                String mobileNo = getTrimmedParameter(request, "mobileNo");
                String emailId = getTrimmedParameter(request, "emailId");
                String accountType = getTrimmedParameter(request, "accountType");
                String dateOfBirth = getTrimmedParameter(request, "dateOfBirth");
                String idProof = getTrimmedParameter(request, "idProof");

                // Establish database connection and prepare SQL statement
                Connection con = DBConnection.getConnection();
                String updateSQL = "UPDATE customer SET full_name=?, address=?, mobile_no=?, email_id=?, account_type=?, initial_balance=?, date_of_birth=?, id_proof=? WHERE customer_id=?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);
                pstmt.setString(1, fullName);
                pstmt.setString(2, address);
                pstmt.setString(3, mobileNo);
                pstmt.setString(4, emailId);
                pstmt.setString(5, accountType);
                pstmt.setDouble(6, initialBalance);
                pstmt.setString(7, dateOfBirth);
                pstmt.setString(8, idProof);
                pstmt.setInt(9, customerId);

                // Execute the update
                int rowsUpdated = pstmt.executeUpdate();

                // Close resources
                pstmt.close();
                con.close();

                // Redirect based on update result
                if (rowsUpdated > 0) {
                    response.sendRedirect("viewCustomers.jsp?message=Customer updated successfully");
                } else {
                    response.sendRedirect("viewCustomers.jsp?message=Update failed");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("viewCustomers.jsp?message=Error updating customer");
            }
        }
    }

    // Utility method to retrieve and trim parameter values
    private String getTrimmedParameter(HttpServletRequest request, String paramName) {
        String param = request.getParameter(paramName);
        return param != null ? param.trim() : "";
    }
}
