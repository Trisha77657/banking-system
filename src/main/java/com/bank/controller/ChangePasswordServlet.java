package com.bank.controller;
import com.bank.dao.ChangePasswordDAO;
import com.bank.dao.ChangePasswordDAOImpl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ChangePasswordDAO changePasswordDAO;

    public void init() {
        changePasswordDAO = new ChangePasswordDAOImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("accountNo") == null) {
            // If session is not valid, redirect to login page
            response.sendRedirect("customer_login.jsp");
            return;
        }

        int accountNo = (int) session.getAttribute("accountNo");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        // Verify old password
       
        	
        
        boolean isOldPasswordCorrect = changePasswordDAO.verifyOldPassword(accountNo, oldPassword);
        if (isOldPasswordCorrect) {
            // Update password
            boolean passwordChanged = changePasswordDAO.updatePassword(accountNo, newPassword);
            if (passwordChanged) {
                response.getWriter().println("Password changed successfully.");
            } else {
                response.getWriter().println("Failed to update password.");
            }
        } else {
            response.getWriter().println("Old password is incorrect.");
        }
        
    }
}