package com.bank.controller;

import com.bank.dao.AdminDAO;
import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private AdminDAO adminDAO;
    private CustomerDAO customerDAO;

    public void init() {
        adminDAO = new AdminDAO();
        customerDAO = new CustomerDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("login".equals(action)) {
            loginAdmin(request, response);
        } else if ("registerCustomer".equals(action)) {
            registerCustomer(request, response);
        }
        else if ("logout".equals(action)) {
        	response.sendRedirect("admin_login.jsp");
        
        }
        else if("View All Customers".equals(action)) {
        	response.sendRedirect("viewCustomers.jsp");
        }
    }

    private void loginAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (adminDAO.validateAdmin(username, password)) {
            request.getSession().setAttribute("adminUsername", username);
            response.sendRedirect("admin_dashboard.jsp");
        } else {
            response.sendRedirect("admin_login_fail.jsp");
            
        }
        
    }

    private void registerCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String mobileNo = request.getParameter("mobileNo");
        String emailId = request.getParameter("emailId");
        String accountType = request.getParameter("accountType");
        double initialBalance = Double.parseDouble(request.getParameter("initialBalance"));
        String dateOfBirth = request.getParameter("dateOfBirth");
        String idProof = request.getParameter("idProof");

        String accountNo = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        String temporaryPassword = UUID.randomUUID().toString().replace("-", "").substring(0, 8);

        Customer customer = new Customer();
        customer.setFullName(fullName);
        customer.setAddress(address);
        customer.setMobileNo(mobileNo);
        customer.setEmailId(emailId);
        customer.setAccountType(accountType);
        customer.setInitialBalance(initialBalance);
        customer.setDateOfBirth(java.sql.Date.valueOf(dateOfBirth));
        customer.setIdProof(idProof);
        customer.setAccountNo(accountNo);
        customer.setPassword(temporaryPassword);
        customer.setStatus("Active");

        customerDAO.registerCustomer(customer);

        request.setAttribute("accountNo", accountNo);
        request.setAttribute("temporaryPassword", temporaryPassword);
        request.getRequestDispatcher("register_success.jsp").forward(request, response);
    }
}