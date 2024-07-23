package com.bank.controller;

import com.bank.dao.CustomerDAO;
import com.bank.dao.TransactionDao;

import com.bank.model.Customer;
import com.bank.model.Transaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerDAO customerDAO;
    private TransactionDao transactionDAO;

    public void init() {
        customerDAO = new CustomerDAO();
        transactionDAO = new TransactionDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("login".equals(action)) {
            loginCustomer(request, response);
        } else if ("deposit".equals(action)) {
            deposit(request, response);
        } else if ("withdraw".equals(action)) {
            withdraw(request, response);
        } else if ("closeAccount".equals(action)) {
            closeAccount(request, response);
        }
    }

    private void loginCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");
        String password = request.getParameter("password");
        Customer customer = customerDAO.getCustomerByAccountNoAndPassword(accountNo, password);
        if (customer != null) {
            request.getSession().setAttribute("customerAccountNo", accountNo);
            response.sendRedirect("customer_dashboard.jsp");
        } else {
            response.sendRedirect("customerloginfail.jsp");
        }
    }

    private void deposit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = (String) request.getSession().getAttribute("customerAccountNo");
        double amount = Double.parseDouble(request.getParameter("amount"));
        customerDAO.updateBalance(accountNo, amount, "Deposit");
        Transaction transaction = new Transaction(0, "", accountNo, amount, null);
        transaction.setAccountNo(accountNo);
        transaction.setTransactionType("Deposit");
        transaction.setAmount(amount);
        transactionDAO.addTransaction(transaction);
        response.sendRedirect("customer_dashboard.jsp");
    }

    private void withdraw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = (String) request.getSession().getAttribute("customerAccountNo");
        double amount = Double.parseDouble(request.getParameter("amount"));
        if (customerDAO.updateBalance(accountNo, -amount, "Withdraw")) {
            Transaction transaction = new Transaction(0, "", accountNo, amount, null);
            transaction.setAccountNo(accountNo);
            transaction.setTransactionType("Withdraw");
            transaction.setAmount(amount);
            transactionDAO.addTransaction(transaction);
            response.sendRedirect("customer_dashboard.jsp");
        } else {
            response.sendRedirect("withdraw.jsp?error=Insufficient+Balance");
        }
    }

    private void closeAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = (String) request.getSession().getAttribute("customerAccountNo");
        customerDAO.closeAccount(accountNo);
        response.sendRedirect("customer_login.jsp?message=Account+Closed+Successfully");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerDAO customerDAO = new CustomerDAO();
        List<Customer> customerList = customerDAO.getAllCustomers();
        for (Customer customer : customerList) {
            System.out.println(customer.getFullName()); 
        }
        request.setAttribute("customerList", customerList);
        request.getRequestDispatcher("viewCustomers.jsp").forward(request, response);
    }
}