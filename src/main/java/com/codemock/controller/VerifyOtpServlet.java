package com.codemock.controller;

import com.codemock.dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "VerifyOtpServlet", urlPatterns = {"/VerifyOtpServlet"})
public class VerifyOtpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Get OTP from form and Email from Session
        String enteredOtp = request.getParameter("otp");
        HttpSession session = request.getSession();
        String sessionEmail = (String) session.getAttribute("tempEmail");

        if (sessionEmail != null && enteredOtp != null) {
            
            // 2. Pass to DAO to verify and activate
            UserDAO userDao = new UserDAO();
            boolean isSuccess = userDao.verifyAndActivateUser(sessionEmail, enteredOtp);

            if (isSuccess) {
                // Verification successful! Clear temp email and send to login page
                session.removeAttribute("tempEmail");
                
                // Real project me hum yahan success message bhejte hain, abhi ke liye seedha index par
                response.sendRedirect("index.jsp?verified=true");
            } else {
                // Invalid or Expired OTP
                response.getWriter().print("Invalid or Expired OTP. Please try again.");
            }
        } else {
            response.sendRedirect("index.jsp");
        }
    }
    
    
}