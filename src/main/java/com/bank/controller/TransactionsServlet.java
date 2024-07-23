package com.bank.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.dao.TransactionDao;
import com.bank.model.Transaction;

@WebServlet("/transaction")
public class TransactionsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TransactionDao transactionDao = new TransactionDao();
        List<Transaction> transactions = transactionDao.getLast10Transactions();

        request.setAttribute("transactions", transactions);
        request.getRequestDispatcher("view_transactions.jsp").forward(request, response);
    }
}